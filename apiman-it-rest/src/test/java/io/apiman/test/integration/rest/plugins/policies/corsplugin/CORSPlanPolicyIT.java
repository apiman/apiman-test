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

package io.apiman.test.integration.rest.plugins.policies.corsplugin;

import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.plans.PlanBean;

/**
 * @author jkaspar
 */
public class CORSPlanPolicyIT extends AbstractCORSPolicyIT {

    @ApiVersion(api = "api", vPlans = {"plan", "corsValidationPlan"})
    @ManagedEndpoint
    private static String endpoint;

    @Plan(organization = "organization")
    @PlanVersion(policies = @Policies(value = "plugins/cors_001", params = {"error", "false"}))
    private static PlanBean plan;

    @Plan(organization = "organization")
    @PlanVersion(policies = @Policies(value = "plugins/cors_001", params = {"error", "true"}))
    private static PlanBean corsValidationPlan;

    @Client(organization = "organization")
    @ClientVersion(contracts = {
        @Contract(vPlan = "plan", vApi = "endpoint"),
        @Contract(vPlan = "corsValidationPlan", vApi = "endpoint")})
    private static ClientVersionBean clientVersion;

    @ApiKey(vPlan = "plan", vApi = "endpoint", vClient = "clientVersion")
    private static String apikey;

    @ApiKey(vPlan = "corsValidationPlan", vApi = "endpoint", vClient = "clientVersion")
    private static String corsValidationApikey;

    @Override
    protected String getCorsValidationResourceURL() {
        return addApiKeyParameter(endpoint, corsValidationApikey);
    }

    @Override
    protected String getResourceURL() {
        return addApiKeyParameter(endpoint, apikey);
    }
}
