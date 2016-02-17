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

import io.apiman.manager.api.beans.metrics.UsagePerClientBean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author jkaspar
 */
public class ClientUsageMetricsIT extends AbstractMetricsIT {

    @Test
    public void requestsAfterIntervalAreNotIncluded() throws Exception {
        UsagePerClientBean metricsBefore = apiVersions.metricsClientUsage(beforeRecoding, afterRecording);
        recordSuccessfulRequests(1, apiKey_Client2plan1);
        recordFailedRequests(2, apiKey_Client2plan1);
        TimeUnit.SECONDS.sleep(TIME_DELAY);
        UsagePerClientBean metricsAfter = apiVersions.metricsClientUsage(beforeRecoding, afterRecording);

        assertEquals("Unexpected metrics data",
                metricsBefore.getData().get(client2.getId()),
                metricsAfter.getData().get(client2.getId()));
    }

    @Test
    public void sumsOfRequestsForEachClientAreCorrect() throws Exception {
        UsagePerClientBean metrics = apiVersions.metricsClientUsage(beforeRecoding, afterRecording);
        assertEquals("Unexpected metrics data for client",
                new Long(CLIENT1_FAIL + CLIENT1_SUCC),
                metrics.getData().get(client.getId()));

        assertEquals("Unexpected metrics data for client2",
                new Long(CLIENT2_FAIL + CLIENT2_SUCC),
                metrics.getData().get(client2.getId()));
    }
}
