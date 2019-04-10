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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.entity.Contracts;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateContractPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.plans.PlanBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import com.codeborne.selenide.Selenide;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class CreateContractIT extends AbstractUITest {

    @PlanVersion(plan = "plan")
    private static PlanVersionBean planVersion;

    @Plan(organization = "organization")
    private static PlanBean secondPlan;

    @PlanVersion(plan = "secondPlan")
    private static PlanVersionBean secondPlanVersion;

    @ApiVersion(api = "api", unique = true, isPublic = false, vPlans = {"planVersion", "secondPlanVersion"})
    private static ApiVersionBean firstAPI;

    @ApiVersion(api = "api", unique = true, isPublic = false, vPlans = {"planVersion", "secondPlanVersion"})
    private static ApiVersionBean secondAPI;

    @ApiVersion(api = "api", publish = false)
    private static ApiVersionBean unpublishAPI;

    @ClientVersion(client = "client", unique = true)
    private static ClientVersionBean firstClientVersion;

    @ClientVersion(client = "client", unique = true)
    private static ClientVersionBean secondClientVersion;

    CreateContractPage page;

    @Before
    public void setUp() throws Exception {
        page = open(CreateContractPage.class);
    }

    @Test
    public void shouldListAllAvailableVersionsForSelectedClient() throws Exception {
        page.clientSelect().select(client.getName());

        page.clientVersionSelect().open();

        page.clientVersionSelect().options().findBy(text(firstClientVersion.getVersion())).should(exist);
        page.clientVersionSelect().options().findBy(text(secondClientVersion.getVersion())).should(exist);
    }

    @Test
    public void shouldListAllAvailablePlansForSelectedApi() throws Exception {
        page.selectApiVersion(api.getName(), firstAPI.getVersion());

        page.planSelect().open();
        page.planSelect().options().findBy(text(plan.getName())).should(exist);
        page.planSelect().options().findBy(text(secondPlan.getName())).should(exist);
    }

    @Test
    public void shouldListOnlyPublishedApis() throws Exception {
        page.selectApi(api.getName());
        page.apiVersionSelect().open();
        page.apiVersionSelect().options()
            .findBy(text(firstAPI.getVersion()))
            .should(exist);
        page.apiVersionSelect().options()
            .findBy(text(secondAPI.getVersion()))
            .should(exist);

        page.apiVersionSelect().options().shouldHaveSize(2);
    }

    @Test
    public void shouldListCorrectApiVersionWhenSelected() throws Exception {
        selectAndCheckApiVersion(firstAPI.getVersion());
        selectAndCheckApiVersion(secondAPI.getVersion());
    }

    @Test
    public void canCreateContractWithOldestVersionOfApi() throws Exception {
        createContract(firstClientVersion, firstAPI);
    }

    @Test
    public void canCreateContractWithNewestVersionOfApi(){
        createContract(secondClientVersion, secondAPI);
    }
    
    private void createContract(ClientVersionBean clientVersion, ApiVersionBean apiVersion){
        page.selectClientVersion(client.getName(), clientVersion.getVersion())
            .selectApiVersion(api.getName(), apiVersion.getVersion())
            .create();

        assertThat(getDescriptionOfContract(), containsString(apiVersion.getVersion()));

        ClientVersions clientVersions = new ClientVersions(client);
        clientVersions.fetch(clientVersion.getVersion());
        Contracts contractClient = clientVersions.contracts();

        contractClient.fetch(organization, apiVersion, planVersion);
        assertThat(contractClient.getBean(), is(not(null)));
    }

    private void selectAndCheckApiVersion(String version){
        page.selectApiVersion(api.getName(), version);
        page.selectedApiButton().shouldHave(
            and("Selected api version button displays api name and api version",
                text(api.getName()), text(version)));
    }

    private String getDescriptionOfContract(){
        return Selenide.$("div.versionAndPlan").getText();
    }
}
