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

import io.apiman.test.integration.Suite;
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
 * Diagram describing contracts and entities
 *
 *                                  +-----------------+
 *                                  |                 |
 *                                  |   apiVersion    |
 *                   +----------+   |                 |   +-----------+
 *                   |          |   +--------+--------+   |           |
 *                   |   plan   |            |            |   plan2   |
 *                   |          |            |            |           |
 *                   +-----+----+            |            +-----+-----+
 *                         |                 |                  |
 *                +--------+--------+        |         +--------+---------+
 *                |   planVersion   | <------+-------> |   plan2Version   |
 *                +-----------------+                  +------------------+
 *                      ^   ^                                   ^
 *                      |   |                                   |
 *                      |   |                                   |
 *            +---------+   +----------+                        +---+
 *            |                        |                            |
 *            |                        |                            |
 *            |                        |                            |
 *  +---------+----------+  +----------+---------+  +---------------+----------------+
 *  |   clientVersion1   |  |   clientVersion2   |  |   singleVersionClientVersion   |
 *  +---------+----------+  +----------+---------+  +---------------+----------------+
 *            |                        |                            |
 *            +------------+-----------+                            |
 *                         |                                        |
 *                   +-----+------+                     +-----------+-------------+
 *                   |            |                     |                         |
 *                   |   client   |                     |   singleVersionClient   |
 *                   |            |                     |                         |
 *                   +------------+                     +-------------------------+
 *
 * @author jkaspar
 */
public abstract class AbstractMetricsIT extends AbstractTest {

    protected static final int TIME_DELAY = Integer.valueOf(SuiteProperties.getProperty("apiman.test.delay"));
    protected ApiVersions apiVersions;
    protected Date beforeRecoding;
    protected Date afterRecording;

    protected static final int CLIENT_V1_SUCC = 1;
    protected static final int CLIENT_V1_FAIL = 2;
    protected static final int CLIENT_V2_SUCC = 3;
    protected static final int CLIENT_V2_FAIL = 4;
    protected static final int SINGLE_VERSION_CLIENT_SUCC = 5;
    protected static final int SINGLE_VERSION_CLIENT_FAIL = 6;
    protected static final int PUBLIC_SUCC = 7;
    protected static final int PUBLIC_FAIL = 8;

    // Client metrics count
    protected static final int CLIENT_SUCC = CLIENT_V1_SUCC + CLIENT_V2_SUCC;
    protected static final int CLIENT_FAIL = CLIENT_V1_FAIL + CLIENT_V2_FAIL;

    // Plan metrics count
    protected static final int PLAN1_SUCC = CLIENT_V1_SUCC + CLIENT_V2_SUCC;
    protected static final int PLAN1_FAIL = CLIENT_V1_FAIL + CLIENT_V2_FAIL;
    protected static final int PLAN2_SUCC = SINGLE_VERSION_CLIENT_SUCC;
    protected static final int PLAN2_FAIL = SINGLE_VERSION_CLIENT_FAIL;

    // Total metrics count
    protected static final long TOTAL_FAILURES = CLIENT_FAIL + SINGLE_VERSION_CLIENT_FAIL + PUBLIC_FAIL;
    protected static final long TOTAL_REQUESTS = CLIENT_SUCC + SINGLE_VERSION_CLIENT_SUCC + PUBLIC_SUCC + TOTAL_FAILURES;

    @ApiVersion(api = "api",
        vPlans = {"planVersion", "plan2Version"},
        policies = @Policies("metrics_001"),
        unique = true,
        isPublic = true)
    protected ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    protected String endpoint;

    @PlanVersion(plan = "plan")
    protected static PlanVersionBean planVersion;

    @Plan(organization = "organization", name = "TestPlan2_")
    protected static PlanBean plan2;

    @PlanVersion(plan = "plan2")
    protected static PlanVersionBean plan2Version;

    @ClientVersion(client = "client", unique = true, contracts = {
        @Contract(vPlan = "planVersion", vApi = "apiVersion")})
    protected ClientVersionBean clientVersion1;

    @ClientVersion(client = "client", unique = true, contracts = {
        @Contract(vPlan = "planVersion", vApi = "apiVersion")})
    protected ClientVersionBean clientVersion2;

    @Client(organization = "organization", name = "SingleVersionClient")
    protected static ClientBean singleVersionClient;

    @ClientVersion(client = "singleVersionClient", unique = true, contracts = {
        @Contract(vPlan = "plan2Version", vApi = "apiVersion")})
    protected ClientVersionBean singleVersionClientVersion;

    @ApiKey(vApi = "apiVersion", vPlan = "planVersion", vClient = "clientVersion1")
    protected String apiKey_clientVersion1;

    @ApiKey(vApi = "apiVersion", vPlan = "planVersion", vClient = "clientVersion2")
    protected String apiKey_clientVersion2;

    @ApiKey(vApi = "apiVersion", vPlan = "plan2Version", vClient = "singleVersionClientVersion")
    protected String apiKey_singleVersionClient;

    @Before
    public void setUpClient() throws Exception {
        apiVersions = new ApiVersions(api);
        apiVersions.fetch(apiVersion.getVersion());
    }

    @Before
    public void recordMetrics() throws Exception {
        beforeRecoding = new Date();
        Suite.waitForAction();

        recordSuccessfulRequests(CLIENT_V1_SUCC, apiKey_clientVersion1);
        recordFailedRequests(CLIENT_V1_FAIL, apiKey_clientVersion1);

        recordSuccessfulRequests(CLIENT_V2_SUCC, apiKey_clientVersion2);
        recordFailedRequests(CLIENT_V2_FAIL, apiKey_clientVersion2);

        recordSuccessfulRequests(SINGLE_VERSION_CLIENT_SUCC, apiKey_singleVersionClient);
        recordFailedRequests(SINGLE_VERSION_CLIENT_FAIL, apiKey_singleVersionClient);

        recordSuccessfulRequests(PUBLIC_SUCC);
        recordFailedRequests(PUBLIC_FAIL);

        afterRecording = new Date();
        TimeUnit.SECONDS.sleep(TIME_DELAY);
    }

    protected void recordSuccessfulRequests(int count) {
        for (int i = 0; i < count; i++) {
            givenGateway().get(endpoint);
        }
    }

    protected void recordFailedRequests(int count) {
        for (int i = 0; i < count; i++) {
            givenGateway().get(endpoint + "/foo");
        }
    }

    protected void recordSuccessfulRequests(int count, String apiKey) {
        for (int i = 0; i < count; i++) {
            givenGateway().get(addApiKeyParameter(endpoint, apiKey));
        }
    }

    protected void recordFailedRequests(int count, String apiKey) {
        for (int i = 0; i < count; i++) {
            givenGateway().get(addApiKeyParameter(endpoint + "/foo", apiKey));
        }
    }
}
