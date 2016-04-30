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
import io.apiman.manager.api.beans.metrics.UsagePerPlanBean;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({MetricTest.class, LongRunningTest.class})
public class PlanUsageMetricsIT extends AbstractMetricsIT {

    @Test
    public void shouldHaveCorrectSumsOfRequestsForEachPlan() throws Exception {
        UsagePerPlanBean metrics = apiVersions.metricsPlanUsage(beforeRecoding, afterRecording);
        assertEquals("Unexpected metrics data for Plan",
            new Long(PLAN1_SUCC + PLAN1_FAIL),
            metrics.getData().get(plan.getId()));

        assertEquals("Unexpected metrics data for Plan2",
            new Long(PLAN2_SUCC + PLAN2_FAIL),
            metrics.getData().get(plan2.getId()));
    }
}
