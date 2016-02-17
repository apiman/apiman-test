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
import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.pages.policies.PoliciesDetailPage;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: APIMAN-90 - Policy UI - Plan Policies UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    apimanui/api-manager/orgs/{0}/plans/{1}/{2}/policies - PoliciesDetailPage.java
 *
 */
@RunWith(ApimanRunner.class)
public class PlanPolicyIT extends AbstractTest {

    @PlanVersion(plan = "plan", policies = @Policies("arbitrary"))
    private static PlanVersionBean version;

    /**
     * Verify expected presence of policy
     */
    @Test
    public void shouldListConfiguredPolicy() {

        PoliciesDetailPage thePoliciesDetailPage = open(PoliciesDetailPage.class, organization.getName(), plan.getId(),
            version.getVersion());

        ElementsCollection thePolicies = thePoliciesDetailPage.policies();
        SelenideElement foundPolicy = thePolicies.find(text("Ignored Resources Policy"));
        thePoliciesDetailPage.policyTitle(foundPolicy).shouldHave(text("Ignored Resources Policy"));
    }

}
