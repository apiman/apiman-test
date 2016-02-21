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

package io.apiman.test.integration.rest.apis.metrics;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;

import io.apiman.test.integration.SuiteProperties;
import io.apiman.test.integration.base.AbstractTest;
import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.plans.PlanBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;

/**
 * @author jkaspar
 */
public abstract class AbstractMetricsIT extends AbstractTest {

    protected static final int TIME_DELAY = Integer.valueOf(SuiteProperties.getProperty("apiman.test.delay"));
    protected ApiVersions apiVersions;
    protected Date beforeRecoding;
    protected Date afterRecording;

    protected static final int CLIENT1v1_SUCC = 1;
    protected static final int CLIENT1v1_FAIL = 2;
    protected static final int CLIENT1v2_SUCC = 3;
    protected static final int CLIENT1v2_FAIL = 4;
    protected static final int CLIENT2_PLAN1_SUCC = 5;
    protected static final int CLIENT2_PLAN1_FAIL = 6;
    protected static final int CLIENT2_PLAN2_SUCC = 7;
    protected static final int CLIENT2_PLAN2_FAIL = 8;
    protected static final int PUBLIC_SUCC = 9;
    protected static final int PUBLIC_FAIL = 10;

    // Client metrics count
    protected static final int CLIENT1_SUCC = CLIENT1v1_SUCC + CLIENT1v2_SUCC;
    protected static final int CLIENT1_FAIL = CLIENT1v1_FAIL + CLIENT1v2_FAIL;
    protected static final int CLIENT2_SUCC = CLIENT2_PLAN1_SUCC + CLIENT2_PLAN2_SUCC;
    protected static final int CLIENT2_FAIL = CLIENT2_PLAN1_FAIL + CLIENT2_PLAN2_FAIL;

    // Plan metrics count
    protected static final int PLAN1_SUCC = CLIENT1v1_SUCC + CLIENT1v2_SUCC + CLIENT2_PLAN1_SUCC;
    protected static final int PLAN1_FAIL = CLIENT1v1_FAIL + CLIENT1v2_FAIL + CLIENT2_PLAN1_FAIL;
    protected static final int PLAN2_SUCC = CLIENT2_PLAN2_SUCC;
    protected static final int PLAN2_FAIL = CLIENT2_PLAN2_FAIL;

    @ApiVersion(api = "api",
        vPlans = {"planVersion", "planVersion2"},
        policies = @Policies("metrics_001"),
        unique = true,
        isPublic = true)
    protected ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    protected String endpoint;

    @PlanVersion(plan = "plan")
    protected static PlanVersionBean planVersion;

    @Plan(organization = "organization")
    protected static PlanBean plan2;

    @PlanVersion(plan = "plan2")
    protected static PlanVersionBean planVersion2;

    @Client(organization = "organization")
    protected static ClientBean client2;

    @ClientVersion(client = "client", unique = true, contracts = {
        @Contract(vPlan = "planVersion", vApi = "apiVersion")})
    protected ClientVersionBean client1version1;

    @ClientVersion(client = "client", unique = true, contracts = {
        @Contract(vPlan = "planVersion", vApi = "apiVersion")})
    protected ClientVersionBean client1Version2;

    @ClientVersion(client = "client2", unique = true, contracts = {
        @Contract(vPlan = "planVersion", vApi = "apiVersion"),
        @Contract(vPlan = "planVersion2", vApi = "apiVersion")})
    protected ClientVersionBean client2Version;

    @ApiKey(vApi = "apiVersion", vPlan = "planVersion", vClient = "client1version1")
    protected String apiKey_Client1v1;

    @ApiKey(vApi = "apiVersion", vPlan = "planVersion", vClient = "client1Version2")
    protected String apiKey_Client1v2;

    @ApiKey(vApi = "apiVersion", vPlan = "planVersion", vClient = "client2Version")
    protected String apiKey_Client2plan1;

    @ApiKey(vApi = "apiVersion", vPlan = "planVersion2", vClient = "client2Version")
    protected String apiKey_Client2plan2;

    @Before
    public void setUpClient() throws Exception {
        apiVersions = new ApiVersions(api);
        apiVersions.fetch(apiVersion.getVersion());
    }

    @Before
    public void recordMetrics() throws Exception {
        beforeRecoding = new Date();

        recordSuccessfulRequests(CLIENT1v1_SUCC, apiKey_Client1v1);
        recordFailedRequests(CLIENT1v1_FAIL, apiKey_Client1v1);

        recordSuccessfulRequests(CLIENT1v2_SUCC, apiKey_Client1v2);
        recordFailedRequests(CLIENT1v2_FAIL, apiKey_Client1v2);

        recordSuccessfulRequests(CLIENT2_PLAN1_SUCC, apiKey_Client2plan1);
        recordFailedRequests(CLIENT2_PLAN1_FAIL, apiKey_Client2plan1);

        recordSuccessfulRequests(CLIENT2_PLAN2_SUCC, apiKey_Client2plan2);
        recordFailedRequests(CLIENT2_PLAN2_FAIL, apiKey_Client2plan2);

        recordSuccessfulRequests(PUBLIC_SUCC);
        recordFailedRequests(PUBLIC_FAIL);

        afterRecording = new Date();
        TimeUnit.SECONDS.sleep(TIME_DELAY);
    }

    protected void recordSuccessfulRequests(int count) {
        for (long i = 0; i < count; i++) {
            givenGateway().get(endpoint);
        }
    }

    protected void recordFailedRequests(int count) {
        for (long i = 0; i < count; i++) {
            givenGateway().get(endpoint + "/foo");
        }
    }

    protected void recordSuccessfulRequests(int count, String apiKey) {
        for (long i = 0; i < count; i++) {
            givenGateway().get(addApiKeyParameter(endpoint, apiKey));
        }
    }

    protected void recordFailedRequests(int count, String apiKey) {
        for (long i = 0; i < count; i++) {
            givenGateway().get(addApiKeyParameter(endpoint + "/foo", apiKey));
        }
    }
}
