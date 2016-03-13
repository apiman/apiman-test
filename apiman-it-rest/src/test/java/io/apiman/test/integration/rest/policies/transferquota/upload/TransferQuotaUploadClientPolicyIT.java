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

package io.apiman.test.integration.rest.policies.transferquota.upload;

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.categories.LongRunningTest;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.manager.api.beans.plans.PlanBean;

import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({PolicyTest.class, LongRunningTest.class})
public class TransferQuotaUploadClientPolicyIT extends AbstractTransferQuotaUploadPolicyIT {

    @ApiVersion(api = "api", vPlans = "plan", endpoint = @Endpoint(DeployedServices.DOWNLOAD_ENDPOINT))
    @ManagedEndpoint
    private static String endpoint;

    @Plan(organization = "organization")
    @PlanVersion
    private static PlanBean plan;

    @Client(organization = "organization")
    @ClientVersion(contracts = @Contract(vApi = "endpoint", vPlan = "plan"),
        policies = @Policies(value = "transfer_001", params = {"direction", "upload"}))
    @ApiKey(vPlan = "plan", vApi = "endpoint")
    private static String apikey;

    @Override
    protected String getResourceURL() {
        return AbstractApiTest.addApiKeyParameter(endpoint, apikey);
    }
}
