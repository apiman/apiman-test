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

import io.apiman.test.integration.base.AbstractTest;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jkaspar
 */
@Plugin(artifactId = "apiman-plugins-config-policy")
public class ConfigPluginPlanPolicyIT extends AbstractTest {

    @PlanVersion(plan = "plan")
    private static PlanVersionBean basicPlanVersion;

    @Plan(organization = "organization", name = "PluginTestPlan")
    @PlanVersion(policies = @Policies("config_001"))
    private static PlanVersionBean pluginPlanVersion;

    @ApiVersion(api = "api", vPlans = {"basicPlanVersion", "pluginPlanVersion"})
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private static String endpoint;

    @ClientVersion(client = "client", contracts = {
            @Contract(vApi = "apiVersion", vPlan = "basicPlanVersion"),
            @Contract(vApi = "apiVersion", vPlan = "pluginPlanVersion")
    })
    private static ClientVersionBean clientVersion;

    @ApiKey(vClient = "clientVersion", vApi = "apiVersion", vPlan = "basicPlanVersion")
    private static String basicApiKey;

    @ApiKey(vClient = "clientVersion", vApi = "apiVersion", vPlan = "pluginPlanVersion")
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
