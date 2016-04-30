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
import io.apiman.manager.api.beans.metrics.ResponseStatsPerClientBean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({MetricTest.class, LongRunningTest.class})
public class ClientResponseStatsMetricsIT extends AbstractMetricsIT {

    @Test
    public void shouldNotIncludeRequestsAfterInterval() throws Exception {
        ResponseStatsPerClientBean metricsBefore = apiVersions
            .metricsClientResponseStats(beforeRecoding, afterRecording);

        Suite.waitFor(10 * 1000, "Waiting %d milliseconds before recording additional metrics.");
        recordFailedRequests(1, apiKey_clientVersion1);
        recordSuccessfulRequests(1, apiKey_clientVersion1);
        Suite.waitFor(10 * 1000, "Waiting %d milliseconds after recording additional metrics.");

        ResponseStatsPerClientBean metricsAfter = apiVersions
            .metricsClientResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of total requests",
            metricsBefore.getData().get(client.getId()).getTotal(),
            metricsAfter.getData().get(client.getId()).getTotal());
    }

    @Test
    public void shouldHaveCorrectNumberOfFailuresForEachClient() throws Exception {
        ResponseStatsPerClientBean metrics = apiVersions.metricsClientResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of failed requests for Client", CLIENT_FAIL,
            metrics.getData().get(client.getId()).getFailures());

        assertEquals("Unexpected number of failed requests for SingleVersionClient", SINGLE_VERSION_CLIENT_FAIL,
            metrics.getData().get(singleVersionClient.getId()).getFailures());
    }

    @Test
    public void shouldHaveCorrectNumberOfTotalRequestsForEachClient() throws Exception {
        ResponseStatsPerClientBean metrics = apiVersions.metricsClientResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of total requests for Client", CLIENT_FAIL + CLIENT_SUCC,
            metrics.getData().get(client.getId()).getTotal());

        assertEquals("Unexpected number of total requests for SingleVersionClient",
            SINGLE_VERSION_CLIENT_FAIL + SINGLE_VERSION_CLIENT_SUCC,
            metrics.getData().get(singleVersionClient.getId()).getTotal());
    }
}
