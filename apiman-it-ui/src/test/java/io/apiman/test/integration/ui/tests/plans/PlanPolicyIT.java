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

package io.apiman.test.integration.ui.tests.plans;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.exist;

import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.plans.detail.PlanPoliciesDetailPage;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Test;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: APIMAN-90 - Policy UI - Plan Policies UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    apimanui/api-manager/orgs/{0}/plans/{1}/{2}/policies - PlanPoliciesDetailPage.java
 *
 */
public class PlanPolicyIT extends AbstractUITest {

    @PlanVersion(plan = "plan", policies = @Policies("ignored_001"))
    private static PlanVersionBean version;

    /**
     * Verify expected presence of policy
     */
    @Test
    public void shouldListConfiguredPolicy() {

        PlanPoliciesDetailPage thePoliciesDetailPage = open(PlanPoliciesDetailPage.class,
            organization.getName(), plan.getId(), version.getVersion());

        thePoliciesDetailPage.entryContainer("Ignored Resources Policy").should(exist);
    }

}
