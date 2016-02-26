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

package io.apiman.test.integration.ui.tests.organizations;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;

import io.apiman.test.integration.base.AbstractTest;
import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.pages.plans.detail.PlanDetailPage;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: APIMAN-43 - Organization UI - Plans UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    /apimanui/api-manager/orgs/{0}/plans/{1} - PlanDetailPage.java
 *
 */
@RunWith(ApimanRunner.class)
@Category({VisualTest.class})
public class OrgPlanIT extends AbstractTest {

    @PlanVersion(plan = "plan")
    public static PlanVersionBean planVersion;

    /**
     * Verify expected presence of plan
     * @throws InterruptedException
     */
    @Test
    public void shouldDisplayCorrectPlanInfo() throws InterruptedException {
        PlanDetailPage thePlanDetailPage = open(PlanDetailPage.class, organization.getName(), plan.getName());
        thePlanDetailPage.description().shouldHave(text(plan.getDescription()));
        thePlanDetailPage.name().shouldHave(text(plan.getName()));
    }

}
