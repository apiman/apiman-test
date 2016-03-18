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

import static com.codeborne.selenide.Condition.text;

import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.plans.CreatePlanVersionPage;
import io.apiman.test.integration.ui.support.selenide.pages.plans.detail.PlanVersionDetailPage;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jrumanov, ldimaggi
 *
 * Acceptance test for requirement: APIMAN-77 - Plan UI - New Plan Version UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    /apimanui/api-manager/orgs/{0}/plans/{1}/{2} - PlanVersionDetailPage.java
 *
 *    Adapted from ClonePlanVersionTest.java (jrumanov)
 *
 */
@Category({VisualTest.class})
public class NewPlanVersionIT extends AbstractUITest {

    private static final String CLONED_NAME = "2.0";

    @PlanVersion(plan = "plan", policies = @Policies("arbitrary"))
    private static PlanVersionBean version;

    /**
     * Verify expected presence of plan version
     */
    @Test
    public void shouldListMultipleAvailableVersions() {
        CreatePlanVersionPage createVersionPage =
            open(CreatePlanVersionPage.class, organization.getId(), plan.getId(), version.getVersion());

        createVersionPage
            .version(CLONED_NAME)
            .clone(true)
            .create();

        PlanVersionDetailPage thePlanVersionDetailPage = open(PlanVersionDetailPage.class, organization.getId(),
            plan.getId(), version.getVersion());
        thePlanVersionDetailPage.description().shouldHave(text(plan.getDescription()));
        thePlanVersionDetailPage.version().shouldHave(text(version.getVersion()));

        thePlanVersionDetailPage = open(PlanVersionDetailPage.class, organization.getId(), plan.getId(), CLONED_NAME);
        thePlanVersionDetailPage.description().shouldHave(text(plan.getDescription()));
        thePlanVersionDetailPage.version().shouldHave(text(CLONED_NAME));
    }
}
