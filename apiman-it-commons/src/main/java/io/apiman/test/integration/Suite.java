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

import static io.apiman.test.integration.SuiteProperties.*;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcechace
 */
public class Suite {

    private static final Logger LOG = LoggerFactory.getLogger(Suite.class);

    public static String getManagerUrl() {
        String protocol = getProperty(APIMAN_MANAGER_PROTOCOL_PROP);
        String host = getProperty(APIMAN_MANAGER_HOST_PROP);
        String port = getProperty(APIMAN_MANAGER_PORT_PROP);

        return String.format("%s://%s:%s/apiman", protocol, host, port);
    }

    public static String getDeploymentUrl() {
        String protocol = getProperty(TOOL_DEPLOY_PROTOCOL_PROP);
        String host = getProperty(TOOL_DEPLOY_HOST_PROP);
        String port = getProperty(TOOL_DEPLOY_PORT_PROP);

        return String.format("%s://%s:%s", protocol, host, port);
    }

    /**
     * Wait for specified amount of time
     * @param amount time in milliseconds
     * @param message message to be logged, time is included using {@link String#format(String, Object...)}
     */
    public static void waitFor(long amount, String message) {
        try {
            LOG.info(String.format(message, amount));
            TimeUnit.MILLISECONDS.sleep(amount);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }

    public static void waitFor(long amount) {
        waitFor(amount, "Waiting %d milliseconds for things to happen...");
    }

    public static void waitForAction() {
        long amount = Long.parseLong(getProperty(TEST_ACTION_DELAY_PROP));
        waitFor(amount);
    }

    public static void waitForSetup() {
        long amount = Long.parseLong(getProperty(TEST_SETUP_DELAY_PROP));
        waitFor(amount, "Waiting %d milliseconds after setup.");
    }

    public static void waitForSetupStep() {
        long amount = Long.parseLong(getProperty(TEST_SETUP_DELAY_PROP));
        waitFor(amount, "Waiting %d milliseconds before next setup.");
    }
}
