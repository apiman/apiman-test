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

import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgApisListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgClientsListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgMembersListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgPlansListPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: [APIMAN-37] - Organization Details UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    apimanui/api-manager/orgs/{org name}/clients - OrgClientsListPage.java
 *    apimanui/api-manager/orgs/{org name}/plans - OrgPlansListPage.java
 *    apimanui/api-manager/orgs/{org name}/apis - OrgApisListPage.java
 *    apimanui/api-manager/orgs/{org name}/members - OrgMembersListPage.java
 *
 */
@Category({VisualTest.class})
public class OrgDetailsIT extends AbstractUITest {

    @PlanVersion(plan = "plan")
    private static PlanVersionBean planVersion;

    @ApiVersion(
        version = "with-plan",
        api = "api",
        publish = false,
        isPublic = false,
        vPlans = {"planVersion"})
    private static ApiVersionBean apiVersionWithPlan;

    /**
     * Verify expected presence of api
     */
    @Test
    public void shouldListExpectedApi() {
        OrgApisListPage theApiPage = open(OrgApisListPage.class, organization.getName());
        theApiPage.entriesContainer().shouldHave(text(api.getName()));
    }

    /**
     * Verify expected presence of client
     */
    @Test
    public void shouldListExpectedClient() {
        OrgClientsListPage theClientsPage = open(OrgClientsListPage.class, organization.getName());
        theClientsPage.entriesContainer().shouldHave(text(client.getName()));
    }

    /**
     * Verify expected presence of plan
     */
    @Test
    public void shouldListExpectedPlan() {
        OrgPlansListPage thePlansPage = open(OrgPlansListPage.class, organization.getName());
        thePlansPage.entriesContainer().shouldHave(text(plan.getName()));
    }

    /**
     * Verify expected presence of admin user
     */
    @Test
    public void shouldListExpectedUsers() {
        OrgMembersListPage theMembersPage = open(OrgMembersListPage.class, organization.getName());
        theMembersPage.entriesContainer().shouldHave(text("Admin User (admin)"));
    }

}
