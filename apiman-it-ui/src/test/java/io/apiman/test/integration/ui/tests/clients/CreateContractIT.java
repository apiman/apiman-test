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

package io.apiman.test.integration.ui.tests.clients;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.*;

import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.entity.Contracts;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractTest;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateContractPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.plans.PlanBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class CreateContractIT extends AbstractTest {

    @PlanVersion(plan = "plan")
    private static PlanVersionBean planVersion;

    @Plan(organization = "organization")
    private static PlanBean secondPlan;

    @PlanVersion(plan = "secondPlan")
    private static PlanVersionBean secondPlanVersion;

    @ApiVersion(api = "api", unique = true, vPlans = {"planVersion", "secondPlanVersion"})
    private static ApiVersionBean publishedApiVersion;

    @ApiVersion(api = "api", unique = true, publish = false)
    private static ApiVersionBean unpublishedApiVersion;

    @ClientVersion(client = "client", unique = true,
        contracts = @Contract(vPlan = "planVersion", vApi = "publishedApiVersion"))
    private static ClientVersionBean registeredClientVersion;

    @ClientVersion(client = "client", unique = true, publish = false)
    private static ClientVersionBean nonRegisteredClientVersion;

    CreateContractPage page;

    @Before
    public void setUp() throws Exception {
        page = open(CreateContractPage.class);
    }

    @Test
    public void shouldListAllAvailableVersionsForSelectedClient() throws Exception {
        page.clientSelect().select(client.getName());

        page.clientVersionSelect().open();

        page.clientVersionSelect().options().findBy(text(nonRegisteredClientVersion.getVersion())).should(exist);
        page.clientVersionSelect().options().findBy(text(registeredClientVersion.getVersion())).should(exist);
    }

    @Test
    public void shouldListAllAvailablePlansForSelectedApi() throws Exception {
        page.selectApiVersion(api.getName(), publishedApiVersion.getVersion());

        page.planSelect().open();
        page.planSelect().options().findBy(text(plan.getName())).should(exist);
        page.planSelect().options().findBy(text(secondPlan.getName())).should(exist);
    }

    @Test
    public void shouldListOnlyPublishedApis() throws Exception {
        page.selectApi(api.getName());
        page.apiVersionSelect().open();
        page.apiVersionSelect().options()
            .findBy(text(publishedApiVersion.getVersion()))
            .should(exist);

        page.apiVersionSelect().options().shouldHaveSize(1);
    }

    @Test
    public void shouldListCorrectApiVersionWhenSelected() throws Exception {
        page.selectApiVersion(api.getName(), publishedApiVersion.getVersion());
        page.selectedApiButton().shouldHave(
            and("Selected api version button displays api name and api version",
                text(api.getName()), text(publishedApiVersion.getVersion())));
    }

    @Test
    public void canCreateContract() throws Exception {
        page.selectClientVersion(client.getName(), nonRegisteredClientVersion.getVersion())
            .selectApiVersion(api.getName(), publishedApiVersion.getVersion())
            .selectPlan(plan.getName())
            .create();

        ClientVersions clientVersions = new ClientVersions(client);
        clientVersions.fetch(nonRegisteredClientVersion.getVersion());
        Contracts contractClient = clientVersions.contracts();

        contractClient.fetch(organization, publishedApiVersion, planVersion);
        Assert.assertNotNull("Contract hasn't been created", contractClient.getBean());
    }
}
