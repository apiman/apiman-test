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

import io.apiman.manager.api.beans.metrics.ResponseStatsPerClientBean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author jkaspar
 */
public class ClientResponseStatsMetricsIT extends AbstractMetricsIT {

    @Test
    public void requestsAfterIntervalAreNotIncluded() throws Exception {
        ResponseStatsPerClientBean metricsBefore = apiVersions
            .metricsClientResponseStats(beforeRecoding, afterRecording);
        recordFailedRequests(1, apiKey_Client1v1);
        recordSuccessfulRequests(1, apiKey_Client1v1);
        TimeUnit.SECONDS.sleep(TIME_DELAY);
        ResponseStatsPerClientBean metricsAfter = apiVersions
            .metricsClientResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of total requests",
            metricsBefore.getData().get(client.getId()).getTotal(),
            metricsAfter.getData().get(client.getId()).getTotal());
    }

    @Test
    public void numberOfFailuresForEachClientIsCorrect() throws Exception {
        ResponseStatsPerClientBean metrics = apiVersions.metricsClientResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of failed requests for client", CLIENT1_FAIL,
            metrics.getData().get(client.getId()).getFailures());

        assertEquals("Unexpected number of failed requests for client2", CLIENT2_FAIL,
            metrics.getData().get(client2.getId()).getFailures());
    }

    @Test
    public void numberOfTotalRequestsForEachClientIsCorrect() throws Exception {
        ResponseStatsPerClientBean metrics = apiVersions.metricsClientResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of total requests for client", CLIENT1_FAIL + CLIENT1_SUCC,
            metrics.getData().get(client.getId()).getTotal());

        assertEquals("Unexpected number of total requests for client", CLIENT2_FAIL + CLIENT2_SUCC,
            metrics.getData().get(client2.getId()).getTotal());
    }
}
