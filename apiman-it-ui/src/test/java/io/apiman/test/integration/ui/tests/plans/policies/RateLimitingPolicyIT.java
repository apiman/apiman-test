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

package io.apiman.test.integration.ui.tests.plans.policies;

import static io.apiman.test.integration.ui.support.assertion.BeanAssert.assertNoPolicies;
import static io.apiman.test.integration.ui.support.assertion.BeanAssert.assertPolicyPresent;
import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.version.PlanVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractPlanTest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.plans.detail.PlanPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddRateLimitPolicyPage;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class RateLimitingPolicyIT extends AbstractPlanTest {

    private PlanVersions planVersions;
    private AddRateLimitPolicyPage addPolicyPage;

    @PlanVersion(plan = "plan", unique = true, publish = false)
    private PlanVersionBean version;

    @Before
    public void setUp() {
        PlanPoliciesDetailPage policiesDetailPage = open(
            PlanPoliciesDetailPage.class,
            organization.getId(),
            plan.getId(),
            version.getVersion()
        );

        planVersions = new PlanVersions(plan);
        planVersions.fetch(version.getVersion());
        assertNoPolicies(planVersions);

        addPolicyPage = policiesDetailPage.addPolicy(AddRateLimitPolicyPage.class);
    }

    @Test
    public void canAddPolicy() {
        addPolicyPage
            .configure(10, "API", "Hour")
            .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent(planVersions, PolicyDefs.RATE_LIMITING);
    }

    @Test
    public void canCancelConfiguration() {
        addPolicyPage
            .configure(10, "API", "Hour")
            .cancel(ApiPoliciesDetailPage.class);
        assertNoPolicies(planVersions);
    }
}
