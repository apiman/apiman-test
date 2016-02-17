/*
 * Copyright 2016 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.test.integration.db;

import static io.apiman.test.integration.db.Constants.PROP_DB_ALLOCATION_TIME_PROP;
import static io.apiman.test.integration.db.Constants.PROP_DB_LABEL;

import io.apiman.tools.ddl.DdlParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.jboss.qa.dballoc.api.allocator.DbAllocatorResource;
import org.jboss.qa.dballoc.api.allocator.entity.JaxbAllocation;
import org.jboss.qa.dballoc.api.executor.SqlExecutor;
import org.jboss.qa.dballoc.api.executor.SqlRequest;
import org.jboss.qa.dballoc.client.RestClientFactory;

/**
 * @author jcechace
 */
public class DBAllocation {
    public static final String REST_ALLOCATOR_ENDPOINT = "http://dballocator.mw.lab.eng.bos.redhat.com:8080/allocator-rest/api";
    public static final String REST_EXECUTOR_ENDPOINT = "http://dballocator.mw.lab.eng.bos.redhat.com:8080/sql-executor/api";
    public static final String DB_ALLOCATION_UUID_PROP = "uuid";

    private DbAllocatorResource client;
    private SqlExecutor executor;
    private JaxbAllocation allocation;
    private Properties allocationProperties;
    private Label label;

    public DBAllocation() {
        client = RestClientFactory.getDbAllocatorResourceRestClient(REST_ALLOCATOR_ENDPOINT);
        executor = RestClientFactory.getSqlExecutorRestClient(REST_EXECUTOR_ENDPOINT);
    }

    public void fromUUID(String uuid) {
        if (allocation != null) {
            throw new IllegalStateException("DB instance already allocated, uuid: ${allocation.uuid}");
        }

        allocation = client.getAllocation(uuid);
        this.label = new Label(getProperties().getProperty(PROP_DB_LABEL));
    }

    public void fromUUID(Path path) {
        Properties props = new Properties();

        try (InputStream is = Files.newInputStream(path)) {
            props.load(is);
            fromUUID(props.getProperty(DB_ALLOCATION_UUID_PROP));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Could not find properties file at " + path, e);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load properties from file at " + path, e);
        }
    }

    /**
     * Save allocation properties
     *
     * @param path output file path
     */
    public void saveProperties(Path path) {
        Properties props = getProperties();
        try (OutputStream os = Files.newOutputStream(path)) {
            props.store(os, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allocate database instance by given label
     *
     * @param label database label
     */
    public void allocate(String label) {
        if (allocation != null) {
            throw new IllegalStateException("DB instance already allocated, uuid: ${allocation.uuid}");
        }
        this.label = new Label(label);
        String time = System.getProperty(PROP_DB_ALLOCATION_TIME_PROP, "30");
        allocation = client.allocate(label + "&&geo_BOS", "apimanqe", Integer.valueOf(time), true);
    }

    /**
     * Erase and Free allocated DB instance.
     */
    public void free() {
        String uuid = allocation.getUuid().toString();
        client.erase(uuid);
        client.free(uuid);
        allocation = null;
        allocationProperties = null;
    }

    /**
     * Get label associated with allocated database
     *
     * @return label
     */
    public Label getLabel() {
        if (allocation == null) {
            throw new IllegalStateException("No DB instance currently allocated.");
        }
        return label;
    }

    /**
     * Get allocation properties combined with System properties.
     * Defined system properties will overwrite those returned by allocation
     *
     * @return properties
     */
    public Properties getProperties() {
        if (allocation == null) {
            throw new IllegalStateException("No DB instance currently allocated.");
        }

        if (allocationProperties != null) {
            return allocationProperties;
        }

        Properties props = new Properties();
        props.putAll(allocation.getAllocationProperties().toProperties());
        for (String key : System.getProperties().stringPropertyNames()) {
            if (key.startsWith("db") || key.startsWith("apiman")) {
                props.setProperty(key, System.getProperty(key));
            }
        }

        this.allocationProperties = props;
        return props;
    }

    /**
     * Executes given SQL script on allocated DB instance
     *
     * @param sqlScriptPath qlScriptPath Path object referring to SQL script
     * @throws IOException
     */
    public void executeSqlScript(Path sqlScriptPath) throws IOException {
        System.out.println("Executing DDL script: " + sqlScriptPath);

        Properties props = allocation.getAllocationProperties().toProperties();

        SqlRequest sqlRequest = new SqlRequest();
        sqlRequest.setJdbcUrl(props.getProperty("db.jdbc_url"));
        sqlRequest.setUsername(allocation.getAccount().getUsername());
        sqlRequest.setPassword(allocation.getAccount().getPassword());

        List<String> cmds = loadSqlCommands(sqlScriptPath);

        System.out.println("Number of commands to execute: " + cmds.size());

        sqlRequest.setSqlCommands(cmds);

        Response response = executor.execute(sqlRequest, false, 5);

        System.out.println("DDL execution ended with response code " + response.getStatus());

        if (response.getStatus() != 200) {
            for (Map.Entry<String, List<Object>> e : response.getMetadata().entrySet()) {
                System.err.println(e.getKey() + "=" + e.getValue());
            }
            printProperties(props);
        }

    }

    /**
     * Clean and Split given SQL script into commands.
     *
     * @param sqlScriptPath Path object referring to SQL script
     * @return List of SQL commands
     * @throws IOException
     */
    private List<String> loadSqlCommands(Path sqlScriptPath) throws IOException {
        DdlParser parser = new DdlParser();
        return parser.parse(sqlScriptPath.toFile());
    }

    /**
     * Print properties to standard error output
     * @param props properties object
     */
    private void printProperties(Properties props) {
        System.err.println("Allocation Properties\n===========");
        for (String prop : props.stringPropertyNames()) {
            System.err.println(prop + "=" + props.getProperty(prop));
        }
    }
}
