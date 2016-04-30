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

package io.apiman.test.integration.rest.clients;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;

import static org.junit.Assert.assertEquals;

import io.apiman.test.integration.Suite;
import io.apiman.test.integration.SuiteProperties;
import io.apiman.test.integration.base.AbstractClientTest;
import io.apiman.test.integration.categories.MetricTest;
import io.apiman.test.integration.categories.SmokeTest;
import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.metrics.ClientUsagePerApiBean;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({MetricTest.class, SmokeTest.class})
public class ApiUsageMetricsIT extends AbstractClientTest {

    private static final int SUCCESSFUL_REQUESTS = 10;
    private static final int FAILED_REQUESTS = 11;
    private static final int SECOND_API_SUCCESSFUL_REQUESTS = 22;
    private static final int SECOND_API_FAILED_REQUESTS = 20;

    ClientVersions clientVersions;
    private LocalDateTime beforeRecoding;
    private LocalDateTime afterRecording;

    @Api(organization = "organization")
    private static ApiBean secondApi;

    @ApiVersion(api = "secondApi", vPlans = "planVersion")
    private static ApiVersionBean secondApiVersion;

    @ManagedEndpoint("secondApiVersion")
    private static String secondEdpoint;

    @ClientVersion(client = "client", policies = @Policies("metrics_001"), unique = true, contracts = {
        @Contract(vPlan = "planVersion", vApi = "apiVersion"),
        @Contract(vPlan = "planVersion", vApi = "secondApiVersion")})
    private ClientVersionBean clientVersion;

    @ApiKey(vApi = "apiVersion", vPlan = "planVersion", vClient = "clientVersion")
    private String apiKey;

    @ApiKey(vApi = "secondApiVersion", vPlan = "planVersion", vClient = "clientVersion")
    private String secondApiKey;

    private static void recordSuccessfulRequests(int count, String endpoint, String apiKey)
        throws InterruptedException {
        for (int i = 0; i < count; i++) {
            givenGateway().get(addApiKeyParameter(endpoint, apiKey));
        }
    }

    private static void recordFailedRequests(int count, String endpoint, String apiKey) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            givenGateway().get(addApiKeyParameter(endpoint + "/foo", apiKey));
        }
    }

    @Before
    public void setUp() throws Exception {
        beforeRecoding = LocalDateTime.now(ZoneOffset.UTC);
        Suite.waitFor(10 * 1000, "Waiting %d milliseconds before recording metrics");

        recordFailedRequests(FAILED_REQUESTS, endpoint, apiKey);
        recordSuccessfulRequests(SUCCESSFUL_REQUESTS, endpoint, apiKey);

        recordSuccessfulRequests(SECOND_API_SUCCESSFUL_REQUESTS, secondEdpoint, secondApiKey);
        recordFailedRequests(SECOND_API_FAILED_REQUESTS, secondEdpoint, secondApiKey);

        Suite.waitFor(20 * 1000, "Waiting %d milliseconds for metrics to be recorded");
        afterRecording = LocalDateTime.now(ZoneOffset.UTC);;

        clientVersions = new ClientVersions(clientVersion.getClient());
        clientVersions.fetch(clientVersion.getVersion());
    }

    @Test
    public void shouldNotIncludeRequestsAfterInterval() throws Exception {
        ClientUsagePerApiBean metricsBefore = clientVersions.metrics(beforeRecoding, afterRecording);

        Suite.waitFor(10 * 1000, "Waiting %d milliseconds before recording additional metrics.");
        recordSuccessfulRequests(10, endpoint, apiKey);
        Suite.waitFor(10 * 1000, "Waiting %d milliseconds after recording additional metrics.");

        ClientUsagePerApiBean metricsAfter = clientVersions.metrics(beforeRecoding, afterRecording);

        assertEquals("Unexpected metrics data",
            metricsBefore.getData().get(api.getId()),
            metricsAfter.getData().get(api.getId()));
    }

    @Test
    public void shouldHaveCorrectSumOfRequestForEachApi() throws Exception {
        ClientUsagePerApiBean metrics = clientVersions.metrics(beforeRecoding, afterRecording);
        assertEquals("Unexpected metrics data",
            new Long(SUCCESSFUL_REQUESTS + FAILED_REQUESTS),
            metrics.getData().get(api.getId()));

        assertEquals("Unexpected metrics data",
            new Long(SECOND_API_SUCCESSFUL_REQUESTS + SECOND_API_FAILED_REQUESTS),
            metrics.getData().get(secondApi.getId()));
    }
}
