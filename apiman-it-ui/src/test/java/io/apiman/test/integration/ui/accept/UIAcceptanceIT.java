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

package io.apiman.test.integration.ui.accept;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertTrue;

import io.apiman.test.integration.ui.support.assertion.ActivityAssert;
import io.apiman.test.integration.ui.support.assertion.ActivityUIAssert;
import io.apiman.test.integration.ui.support.beanutils.ApiUtils;
import io.apiman.test.integration.ui.support.beanutils.ClientUtils;
import io.apiman.test.integration.ui.support.beanutils.OrgUtils;
import io.apiman.test.integration.ui.support.beanutils.PlanUtils;
import io.apiman.test.integration.ui.support.beanutils.UserUtils;
import io.apiman.test.integration.ui.support.selenide.SelenideUtils;
import io.apiman.test.integration.ui.support.selenide.base.AbstractSimpleUITest;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;
import io.apiman.test.integration.ui.support.selenide.layouts.OrgEntitiesListPage;
import io.apiman.test.integration.ui.support.selenide.pages.HomePage;
import io.apiman.test.integration.ui.support.selenide.pages.LoginPage;
import io.apiman.test.integration.ui.support.selenide.pages.RegistrationPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateClientPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.CreateOrgPage;
import io.apiman.test.integration.ui.support.selenide.pages.plans.CreatePlanPage;
import io.apiman.test.integration.ui.support.selenide.pages.plans.detail.PlanDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.plans.detail.PlanPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddRateLimitPolicyPage;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.idm.UserBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.PlanBean;

import com.codeborne.selenide.Selenide;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * Test as defined in apiman test plan - Acceptance test
 *
 * This test is an automated representation of the steps first described here: 
 * http://java.dzone.com/articles/impatient-new-users
 *
 * @author ldimaggi
 *
 */
@Ignore
public class UIAcceptanceIT extends AbstractSimpleUITest {

    /* Backend test data */
    public UserBean apiDev;
    public UserBean clientDev;
    public OrganizationBean apiOrg;
    public OrganizationBean clientOrg;
    public ApiBean echoApi;
    public ClientBean clientBean;
    public PlanBean planBean;

    /**
     * Set up the backend data for the test - users, organizations, api, client
     */
    @Before
    public void setupTestData() {

        /* Api developer user */
        this.apiDev = UserUtils.local();

        /* Client developer user */
        this.clientDev = UserUtils.local();

        /* Api developer organization */
        this.apiOrg = OrgUtils.local();

        /* Client developer organization */
        this.clientOrg = OrgUtils.local();

        /* Api to be managed */
        this.echoApi = ApiUtils.local(apiOrg);

        /* Client for the api */
        this.clientBean = ClientUtils.local();

        /* Plan for the api */
        this.planBean = PlanUtils.local();
    }

    @BeforeClass
    public static void logoutBefore() {
        logoutIfNeeded();
    }

    @AfterClass
    public static void logoutAfter() {
        logoutIfNeeded();
    }

    public static void logoutIfNeeded() {
        AbstractPage page = page(AbstractPage.class);
        if (page.userMenu().isDisplayed()) {
            page.logout();
        }
    }

    /**
     * Perform steps to register new user
     * @param user user bean
     */
    public HomePage registerUser(UserBean user) {
        String[] name = user.getFullName().split(" ", 2);

        logoutIfNeeded();
        LoginPage loginPage = Selenide.open("/apimanui", LoginPage.class);
        RegistrationPage registrationPage = loginPage.registration()
            .username(user.getUsername())
            .firstname(name[0])
            .lastname(name[1])
            .email(user.getEmail())
            .password("password")
            .passwordConfirm("password");

        return registrationPage.registerComplete();
    }

    /**
     * Perform steps to create new organization
     * @param org organization bean
     */
    public OrgEntitiesListPage createOrganization(OrganizationBean org) {
        CreateOrgPage createOrgPage = SelenideUtils.open(CreateOrgPage.class)
            .name(org.getName())
            .description(org.getDescription());

        return createOrgPage.create();
    }

    /**
     *
     * This test method is an automated representation of the steps first described here:
     * http://java.dzone.com/articles/impatient-new-users
     *
     * The test steps covered are:
     *
     * Manage plans and define plan policies (associated with appropriate organization)
     *
     * Register test users, create organizations
     * As the api producer:
     *   Create a new plan
     *   Add policies to the plan
     *   Define a api
     *   Publish the api to the API Gateway
     * As the api consumer:
     *   Create an client
     *   Search for the api, select a plan
     *   Create a contract for the selected plan
     *   Register the client with the API Gateway
     *   Locate the client's managed endpoint
     *   Call the api, verify that the api policy is enforced
     *
     * The test relies on a api already being deployed to the server - the Jenkins job
     * must take care of this deployment before running the test
     *
     */
    @Test
    public void basicUIAcceptanceScenario() throws InterruptedException {

        Long DELAY = 10000l;

        /* Register api Dev user */
        this.registerUser(apiDev);
        /* Create api dev organization */
        this.createOrganization(apiOrg);

        /* Verify the activity as displayed in the UI */
        ActivityUIAssert.assertActivity(apiOrg, "created organization");
        
        /* Verify the org's creation in the activity log */
        ActivityAssert.assertLatestActivity(apiOrg, "Organization", "Create");

        /* Create plan */
        CreatePlanPage createPlanPage = SelenideUtils.open(CreatePlanPage.class);
        createPlanPage
            .name(planBean.getName())
            .description(planBean.getDescription())
            .create();

        /* Verify the plan's creation in the activity log */
        ActivityAssert.assertLatestActivity(apiOrg, "Plan", "Create");

        /* Add a rate limit policy to the plan */
        PlanDetailPage planDetailPage = page(PlanDetailPage.class);
        PlanPoliciesDetailPage planPoliciesPage = planDetailPage.policies();
        planPoliciesPage.addPolicy(AddRateLimitPolicyPage.class)
            .configure(10, "Api", "Hour")
            .addPolicy(PlanPoliciesDetailPage.class);
        /* Lock plan */
        planPoliciesPage.lock();

        /* Verify the plan's publish in the activity log */
        ActivityAssert.assertLatestActivity(apiOrg, "Plan", "Lock");

        /* Create the api */
        CreateApiPage createApiPage = SelenideUtils.open(CreateApiPage.class);
        createApiPage
            .organization(apiOrg.getName())
            .name(echoApi.getName())
            .description(echoApi.getDescription())
            .version("1.0")
            .create();

        /* Verify the api's creation in the activity log */
        ActivityAssert.assertLatestActivity(apiOrg, "Api", "Create");

        ApiDetailPage apiDetailPage = page(ApiDetailPage.class);
        /* Add implementation to the api */
        apiDetailPage.manageImplementation()
            .endpoint("http://localhost:8080/apiman-echo")
            .apiType("REST")
            .save();
        /* Add plan to the api */
        apiDetailPage.managePlans()
            .addPlan(planBean.getName())
            .save();

        /* Publish the api */
        apiDetailPage.publish();

        /* Verify the api's publishing in the activity log  - waitUntil is required
         * as the publishing action requires more time */
        apiDetailPage.status().waitUntil(text("Published"), DELAY);
        ActivityAssert.assertLatestActivity(apiOrg, "Api", "Publish");

        /* Register client Dev user */
        this.registerUser(clientDev);
        /* Create client dev organization */
        this.createOrganization(clientOrg);
        /* Create an client */
        CreateClientPage createClientPage = SelenideUtils.open(CreateClientPage.class);
        createClientPage
            .name(clientBean.getName())
            .description(clientBean.getDescription())
            .create();

        /* Verify the client's creation in the activity log */
        ActivityAssert.assertLatestActivity(clientOrg, "Client", "Create");

        ClientDetailPage clientDetailPage = page(ClientDetailPage.class);
        /* Create contract and register client */
        clientDetailPage.searchForApis()
            .search(echoApi.getName())
            .openResult(echoApi.getName())
            .createContract(planBean.getName())
            .create();

        /* Verify the client's contract creation in the activity log */
        ActivityAssert.assertLatestActivity(clientOrg, "Client", "CreateContract");

        /* Register client */
        clientDetailPage.register();

        /* Verify the client's registering in the activity log  - waitUntil is required
         * as the registering action requires more time */
        clientDetailPage.status().waitUntil(text("Registered"), DELAY);
        ActivityAssert.assertLatestActivity(clientOrg, "Client", "Register");

        /* Display and verify managed URL */
        String managedAPI = clientDetailPage.manageAPIs()
            .queryParameterInput(text(planBean.getName()))
            .getAttribute("value");
        String regex = "https://localhost:8443/apiman-gateway(.*)apikey=(.*)";
        assertTrue("Managed API " + managedAPI + " doesn't match pattern " + regex, managedAPI.matches(regex));

        clientDetailPage.doneWithDialog();
    }

}
