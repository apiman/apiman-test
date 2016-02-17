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

import io.apiman.manager.api.beans.metrics.ResponseStatsSummaryBean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author jkaspar
 */
public class SummaryResponseStatsMetricsIT extends AbstractMetricsIT {

    @Test
    public void requestsAfterIntervalAreNotIncluded() throws Exception {
        ResponseStatsSummaryBean metricsBefore = apiVersions.metricsSummaryResponseStats(beforeRecoding, afterRecording);
        recordSuccessfulRequests(1);
        recordFailedRequests(2);
        TimeUnit.SECONDS.sleep(TIME_DELAY);
        ResponseStatsSummaryBean metricsAfter = apiVersions.metricsSummaryResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected metrics data", metricsBefore.getTotal(), metricsAfter.getTotal());
    }

    @Test
    public void numberOfTotalRequestsIsCorrect() throws Exception {
        ResponseStatsSummaryBean metrics = apiVersions.metricsSummaryResponseStats(beforeRecoding, afterRecording);
        long totalRequests = CLIENT1_SUCC + CLIENT1_FAIL + CLIENT2_SUCC + CLIENT2_FAIL + PUBLIC_SUCC + PUBLIC_FAIL;

        assertEquals("Unexpected number of total requests", totalRequests, metrics.getTotal());
    }

    @Test
    public void numberOfFailuresIsCorrect() throws Exception {
        ResponseStatsSummaryBean metrics = apiVersions.metricsSummaryResponseStats(beforeRecoding, afterRecording);
        long totalFailures = CLIENT1_FAIL + CLIENT2_FAIL + PUBLIC_FAIL;

        assertEquals("Unexpected number of failed requests", totalFailures, metrics.getFailures());
    }
}
