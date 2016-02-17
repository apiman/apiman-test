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

import static io.apiman.test.integration.db.Constants.*;

import io.apiman.test.integration.db.commands.AddSystemProperty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.jboss.qa.creaper.commands.datasources.AddDataSource;
import org.jboss.qa.creaper.commands.datasources.AddMysqlDataSource;
import org.jboss.qa.creaper.commands.datasources.AddOracleDataSource;
import org.jboss.qa.creaper.commands.datasources.AddPostgreSqlDataSource;
import org.jboss.qa.creaper.core.CommandFailedException;
import org.jboss.qa.creaper.core.ManagementClient;
import org.jboss.qa.creaper.core.offline.OfflineManagementClient;
import org.jboss.qa.creaper.core.offline.OfflineOptions;

/**
 * @author jcechace
 */
public class JdbcDeployer {

    private final Path serverHome;
    private final Path deploymentDir;
    private final Path driverJar;
    private final Path apimanConfig;
    private final Properties allocationProperties;
    private final Label label;

    public JdbcDeployer(Path serverHome, Properties allocationProperties) {
        this.serverHome = serverHome;
        this.deploymentDir = this.serverHome.resolve("standalone/deployments");
        this.apimanConfig = this.serverHome.resolve("standalone/configuration/apiman.properties");
        this.allocationProperties = allocationProperties;
        this.label = new Label(allocationProperties.getProperty(PROP_LABEL));
        this.driverJar = getJdbcJar();
    }

    /**
     * Copy the driverJar JAR into deployment directory
     *
     * @throws IOException
     */
    public void deployDriver() throws IOException {
        Files.copy(driverJar, deploymentDir.resolve(driverJar.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    }

    public Path getJdbcJar() {
        String namedKey = String.format("%s.%s", PROP_JDBC_JAR_LOCATION, label.getName());
        String location = System.getProperty(namedKey, System.getProperty(PROP_JDBC_JAR_LOCATION));

        if (location == null) {
            throw new IllegalStateException("Unknown driver jar location.");
        }

        return Paths.get(location);
    }

    /**
     * Compose the name of deployed driverJar. Usually this is the same as deployment name. In case of MySQL the name
     * is composed as "[deployment_name]_[driver_class]_[major_version]_[minor_version]
     *
     * @return name of deployed driverJar
     */
    public String getDriverName() {
        String deploymentName = driverJar.getFileName().toString();
        if (!deploymentName.startsWith("mysql")) {
            return deploymentName;
        }

        String driverClass = allocationProperties.getProperty("db.jdbc_class");
        int major = label.getMajorVersion();
        int minor = label.getMinorVersion();

        return String.format("%s_%s_%d_%d", deploymentName, driverClass, major, minor);
    }

    /**
     * Add datasource definition into configuration file
     *
     * @param jndiName name of the datasource
     * @throws CommandFailedException
     * @throws Exception
     */
    public void deployDatasource(String jndiName) throws Exception {
        AddDataSource build = getDsBuilder(jndiName)
            .jndiName(jndiName)
            .connectionUrl(allocationProperties.getProperty(PROP_JDBC_URL))
            .driverName(getDriverName())
            .driverClass(allocationProperties.getProperty(PROP_JDBC_CLASS))
            .replaceExisting()
            .enableAfterCreate()
            .usernameAndPassword(
                allocationProperties.getProperty(PROP_USERNAME),
                allocationProperties.getProperty(PROP_PASSWORD)
            ).build();

        OfflineManagementClient managementClient = getManagementClient();
        managementClient.apply(build);

        String dialect = allocationProperties.getProperty("hibernate.dialect");
        String hbm2ddl = allocationProperties.getProperty("apiman.hibernate.hbm2ddl.auto", "validate");

        Properties props = new Properties();
        props.setProperty("apiman.hibernate.dialect", dialect);
        props.setProperty("apiman.hibernate.hbm2ddl.auto", hbm2ddl);
        props.setProperty("apiman.hibernate.connection.datasource", jndiName);
        setApimanProperties(props);
        setWildFlyProperties(props);

    }

    /**
     * Sets system property in apiman.properties

     * @param props properties to be set
     * @throws IOException
     */
    public void setApimanProperties(Properties props) throws IOException {
        Properties apimanProperties = new Properties();
        try (InputStream is = Files.newInputStream(apimanConfig)) {
            apimanProperties.load(is);
        }
        apimanProperties.putAll(props);
        try (OutputStream os = Files.newOutputStream(apimanConfig)) {
            apimanProperties.store(os, null);
        }
    }

    /**
     * Sets system property in configuration file
     * @param props properties to be set
     * @throws IOException
     * @throws CommandFailedException
     */
    public void setWildFlyProperties(Properties props) throws IOException, CommandFailedException {
        OfflineManagementClient managementClient = getManagementClient();
        for (String name : props.stringPropertyNames()) {
            AddSystemProperty build = new AddSystemProperty.Builder(name)
                .value(props.getProperty(name))
                .replaceExisting()
                .build();
            managementClient.apply(build);
        }
    }

    private AddDataSource.Builder getDsBuilder(String name) {
        switch (label.getName()) {
            case "mysql":
                return new AddMysqlDataSource.Builder(name);
            case "postgresql":
                return new AddPostgreSqlDataSource.Builder(name);
            case "oracle":
                return new AddOracleDataSource.Builder(name);
            default:
                throw new IllegalStateException("Unsupported database");
        }
    }

    private OfflineManagementClient getManagementClient() throws IOException {
        return ManagementClient.offline(OfflineOptions
                .standalone()
                .rootDirectory(serverHome.toFile())
                .configurationFile(allocationProperties.getProperty(PROP_APIMAN_CONFIG))
                .build()
        );
    }
}
