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

package io.apiman.test.integration.rest.policies.ipblacklist;

import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.categories.SmokeTest;
import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.plans.PlanBean;

import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({PolicyTest.class})
public class BlacklistClientPolicyIT extends AbstractBlacklistPolicyIT {

    @ManagedEndpoint
    @ApiVersion(api = "api", vPlans = {"plan"})
    private static String endpoint;

    @Plan(organization = "organization")
    @PlanVersion
    private static PlanBean plan;

    @Client(organization = "organization")
    private static ClientBean client;

    @ClientVersion(client = "client", unique = true, contracts = @Contract(vApi = "endpoint", vPlan = "plan"),
        policies = @Policies(value = "iplist_black_001"))
    @ApiKey(vApi = "endpoint", vPlan = "plan")
    private static String loopbackApikey;

    @ClientVersion(client = "client", unique = true, contracts = @Contract(vApi = "endpoint", vPlan = "plan"),
        policies = @Policies(value = "iplist_black_002"))
    @ApiKey(vApi = "endpoint", vPlan = "plan")
    private static String proxyApikey;

    @Override
    protected String getLoopbackResourceURL() {
        return addApiKeyParameter(endpoint, loopbackApikey);
    }

    @Override
    protected String getProxyResourceURL() {
        return addApiKeyParameter(endpoint, proxyApikey);
    }
}
