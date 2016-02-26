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

import io.apiman.test.integration.base.AbstractTest;
import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.pages.plans.PlanActivityListPage;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: APIMAN-80 - Plan UI - Plan Activity UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    apimanui/api-manager/orgs/{0}/plans/{1}/{2}/activity - PlanActivityListPage.java
 *
 */
@RunWith(ApimanRunner.class)
@Category({VisualTest.class})
public class PlanActivityIT extends AbstractTest {

    @PlanVersion(plan = "plan", policies = @Policies("arbitrary"))
    private static PlanVersionBean version;

    /**
     * Verify expected presence of plan entries in organization activity log
     * @throws InterruptedException
     */
    @Test
    public void shouldListCorrectActivities() {

        PlanActivityListPage theActivityPage = open(PlanActivityListPage.class, organization.getName(), plan.getId(),
            version.getVersion());

        theActivityPage.activityRoot().shouldHave(
            text("admin created plan " + organization.getName() + " / " + plan.getName() + " version 1.0"));
        theActivityPage.activityRoot().shouldHave(
            text("admin added a policy to " + organization.getName() + " / " + plan.getName() + " version 1.0"));
    }

}
