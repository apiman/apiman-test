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

package io.apiman.test.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jcechace
 */
public class SuiteProperties {

    private static Properties properties;

    static {
        initProperties();
    }

    // Apiman constants
    public static final String APIMAN_PLUGIN_VERSION_PROP = "apiman.version.plugin";

    // Tools binding address constants
    public static final String TOOL_PROXY_ADDRESS_PROP = "apiman.test.proxy.address";
    public static final String TOOL_PROXY_PORT_PROP = "apiman.test.proxy.port";
    public static final String TOOL_KC_ADDRESS_PROP = "apiman.test.kc.address";
    public static final String TOOL_KC_PORT_PROP = "apiman.test.kc.port";
    public static final String TOOL_KC_REALM_PROP = "apiman.test.kc.realm";

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static Properties getProperties() {
        return properties;
    }

    private static void initProperties() {
        Properties defaults = new Properties();
        try (InputStream is = SuiteProperties.class.getResourceAsStream("/defaults.properties")) {
            defaults.load(is);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        properties = new Properties();

        properties.putAll(defaults);

        for (String key : defaults.stringPropertyNames()) {
            setSystemProperty(key, defaults.getProperty(key));
        }
    }

    private static void setSystemProperty(String key, String rawValue) {
        String system = System.getProperty(key);
        if (system != null) {
            rawValue = system;
        }

        String value = resolvePropertyValue(rawValue);
        if (value != null) {
            properties.setProperty(key, value);
        }
    }

    private static String resolvePropertyValue(String raw) {
        String value = raw;

        int sToken = raw.indexOf("${");
        int eToken = raw.indexOf("}");

        if (sToken != -1 && eToken != -1) {
            String key = raw.substring(sToken + 2, eToken);
            if (key.startsWith("env:")) {
                value = System.getenv(key.substring(4));
            } else {
                value = System.getProperty(key, properties.getProperty(key));
            }
        }

        return value;
    }
}