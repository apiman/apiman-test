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

/**
 * @author jcechace
 */
public class Suite {

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

    public static void waitForAction() {
        long sleepFor = Long.parseLong(getProperty(TEST_ACTION_DELAY_PROP));
        try {
            TimeUnit.SECONDS.sleep(sleepFor);
        } catch (InterruptedException ignored) {
            // ignore
        }
    }
}
