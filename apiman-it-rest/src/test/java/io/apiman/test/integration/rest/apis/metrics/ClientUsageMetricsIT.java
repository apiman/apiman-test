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

import static org.junit.Assert.assertEquals;

import io.apiman.test.integration.Suite;
import io.apiman.test.integration.categories.LongRunningTest;
import io.apiman.test.integration.categories.MetricTest;
import io.apiman.manager.api.beans.metrics.UsagePerClientBean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({MetricTest.class, LongRunningTest.class})
public class ClientUsageMetricsIT extends AbstractMetricsIT {

    @Test
    public void shouldNotIncludeRequestsAfterInterval() throws Exception {
        UsagePerClientBean metricsBefore = apiVersions.metricsClientUsage(beforeRecoding, afterRecording);
        Suite.waitFor(10 * 1000, "Waiting %d milliseconds before recording additional metrics.");

        recordSuccessfulRequests(1, apiKey_singleVersionClient);
        recordFailedRequests(2, apiKey_singleVersionClient);
        Suite.waitForAction();

        UsagePerClientBean metricsAfter = apiVersions.metricsClientUsage(beforeRecoding, afterRecording);

        assertEquals("Unexpected metrics data",
            metricsBefore.getData().get(singleVersionClient.getId()),
            metricsAfter.getData().get(singleVersionClient.getId()));
    }

    @Test
    public void shouldHaveCorrectSumsOfRequestsForEachClient() throws Exception {
        UsagePerClientBean metrics = apiVersions.metricsClientUsage(beforeRecoding, afterRecording);
        assertEquals("Unexpected metrics data for Client",
            new Long(CLIENT_FAIL + CLIENT_SUCC),
            metrics.getData().get(client.getId()));

        assertEquals("Unexpected metrics data for SingleVersionClient",
            new Long(SINGLE_VERSION_CLIENT_FAIL + SINGLE_VERSION_CLIENT_SUCC),
            metrics.getData().get(singleVersionClient.getId()));
    }
}
