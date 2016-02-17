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

import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author jcechace
 */
public class AllocateDatabase {
    public static final String APIMAN_HOME = System.getProperty(PROP_APIMAN_HOME);
    public static final String APIMAN_DB_PROPS = System.getProperty(PROP_DB_PROPS);
    public static final String DB_LABEL = System.getProperty(PROP_DB_LABEL);
    public static final String DB_DS_JNDI_NAME = "java:jboss/datasources/apiman-manager-qe";

    public static void main(String[] args) throws Exception {
        DBAllocation allocation = new DBAllocation();
        allocation.allocate(DB_LABEL);
        allocation.saveProperties(Paths.get(APIMAN_DB_PROPS));

        String ddlScript = getDDLScript(allocation);
        if (ddlScript != null) {
            allocation.executeSqlScript(Paths.get(ddlScript));
        } else {
            System.out.println("No DDL script to be executed.");
        }

        Properties props = allocation.getProperties();
        JdbcDeployer jdbcDeployer = new JdbcDeployer(Paths.get(APIMAN_HOME), props);
        jdbcDeployer.deployDriver();
        jdbcDeployer.deployDatasource(DB_DS_JNDI_NAME);
    }

    private static String getDDLScript(DBAllocation allocation) {
        String key = String.format("%s.%s", PROP_DB_DDL_SCRIPT, allocation.getLabel().getName());
        return allocation.getProperties().getProperty(key, System.getProperty(PROP_JDBC_JAR_LOCATION));
    }

}
