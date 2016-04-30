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

import io.apiman.test.integration.categories.LongRunningTest;
import io.apiman.test.integration.categories.MetricTest;
import io.apiman.manager.api.beans.metrics.HistogramBean;
import io.apiman.manager.api.beans.metrics.HistogramDataPoint;
import io.apiman.manager.api.beans.metrics.HistogramIntervalType;
import io.apiman.manager.api.beans.metrics.ResponseStatsDataPoint;
import io.apiman.manager.api.beans.metrics.ResponseStatsHistogramBean;
import io.apiman.manager.api.beans.metrics.ResponseStatsSummaryBean;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({MetricTest.class, LongRunningTest.class})
public class ResponseStatsMetricsIT extends AbstractIntervalMetricsIT {

    @SuppressWarnings("unchecked")
    @Override
    public HistogramBean<HistogramDataPoint> getMetrics() {
        return (HistogramBean) apiVersions.metricsResponseStats(beforeRecoding, tenMinutesAfterRecording,
            HistogramIntervalType.minute);
    }

    @Test
    public void shouldHaveCorrectValuesInEachSubinterval() throws Exception {
        recordMetricsInFollowingMinute();

        ResponseStatsHistogramBean metrics = apiVersions.metricsResponseStats(beforeRecoding, tenMinutesAfterRecording,
            HistogramIntervalType.minute);

        for (ResponseStatsDataPoint i : metrics.getData()) {
            LocalDateTime subinterval = LocalDateTime.from(formatter.parse(i.getLabel()));
            LocalDateTime endOfSubinterval = subinterval.plusSeconds(59);

            ResponseStatsSummaryBean expectedMetrics = apiVersions
                .metricsSummaryResponseStats(subinterval, endOfSubinterval);

            assertEquals("Unexpected number of total requests for subinterval: " + i.getLabel(),
                expectedMetrics.getTotal(), i.getTotal());
            assertEquals("Unexpected number of failures for subinterval: " + i.getLabel(),
                expectedMetrics.getFailures(), i.getFailures());
            assertEquals("Unexpected number of errors for subinterval: " + i.getLabel(),
                expectedMetrics.getErrors(), i.getErrors());
        }
    }
}
