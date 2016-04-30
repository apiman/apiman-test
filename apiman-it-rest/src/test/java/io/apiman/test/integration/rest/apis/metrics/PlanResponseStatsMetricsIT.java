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
import io.apiman.manager.api.beans.metrics.ResponseStatsPerPlanBean;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({MetricTest.class, LongRunningTest.class})
public class PlanResponseStatsMetricsIT extends AbstractMetricsIT {

    @Test
    public void shouldHaveCorrectNumberOfFailuresForEachPlan() throws Exception {
        ResponseStatsPerPlanBean metrics = apiVersions.metricsPlanResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of failed requests for Plan", PLAN1_FAIL,
            metrics.getData().get(plan.getId()).getFailures());

        assertEquals("Unexpected number of failed requests for Plan2", PLAN2_FAIL,
            metrics.getData().get(plan2.getId()).getFailures());
    }

    @Test
    public void shouldHaveCorrectNumberOfTotalRequestsForEachPlan() throws Exception {
        ResponseStatsPerPlanBean metrics = apiVersions.metricsPlanResponseStats(beforeRecoding, afterRecording);

        assertEquals("Unexpected number of total requests for Plan", PLAN1_SUCC + PLAN1_FAIL,
            metrics.getData().get(plan.getId()).getTotal());

        assertEquals("Unexpected number of total requests for Plan2", PLAN2_SUCC + PLAN2_FAIL,
            metrics.getData().get(plan2.getId()).getTotal());
    }
}
