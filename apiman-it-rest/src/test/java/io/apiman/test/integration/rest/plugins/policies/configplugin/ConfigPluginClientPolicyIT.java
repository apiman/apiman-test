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

package io.apiman.test.integration.rest.plugins.policies.configplugin;

import io.apiman.test.integration.base.AbstractClientTest;
import io.apiman.test.integration.categories.PluginTest;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({PolicyTest.class, PluginTest.class})
@Plugin(artifactId = "apiman-plugins-config-policy")
public class ConfigPluginClientPolicyIT extends AbstractClientTest {

    @ClientVersion(
        client = "client",
        version = "1.0",
        contracts = {@Contract(vApi = "apiVersion", vPlan = "planVersion")}
    )
    private static ClientVersionBean basicClientVersion;

    @ApiKey(vClient = "basicClientVersion", vApi = "apiVersion", vPlan = "planVersion")
    private static String basicApiKey;

    @ClientVersion(
        client = "client",
        version = "2.0",
        contracts = {@Contract(vApi = "apiVersion", vPlan = "planVersion")},
        policies = @Policies("config_001")
    )
    private static ClientVersionBean pluginClientVersion;

    @ApiKey(vClient = "pluginClientVersion", vApi = "apiVersion", vPlan = "planVersion")
    private static String pluginApiKey;

    private static String basicEndpoint;
    private static String pluginEndpoint;

    @BeforeClass
    public static void setUpEndpoints() {
        basicEndpoint = addApiKeyParameter(endpoint, basicApiKey);
        pluginEndpoint = addApiKeyParameter(endpoint, pluginApiKey);
    }

    @Test
    public void shouldContainAddedRequestHeader() throws Exception {
        CommonTestMethods.requestContainsAddedHeaderTest(pluginEndpoint);
    }

    @Test
    public void shouldContainAddedResponseHeader() throws Exception {
        CommonTestMethods.responseContainsAddedHeaderTest(pluginEndpoint);
    }

    @Test
    public void shouldAddOnlySingleRequestHeader() throws Exception {
        CommonTestMethods.pluginAddedJustOneHeaderInResponseTest(basicEndpoint, pluginEndpoint);
    }

    @Test
    public void shouldAddOnlySingleResponseHeader() throws Exception {
        CommonTestMethods.pluginAddedJustOneHeaderInRequestTest(basicEndpoint, pluginEndpoint);
    }
}
