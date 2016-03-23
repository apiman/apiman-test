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

package io.apiman.test.integration.ui.tests.clients.detail;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;

import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.ui.support.selenide.base.AbstractClientUITest;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateContractPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientContractsDetailPage;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class ClientContractsDetailIT extends AbstractClientUITest {

    private static ClientContractsDetailPage page;

    @ClientVersion(client = "client", publish = false,
        contracts = @Contract(vApi = "apiVersion", vPlan = "planVersion"))
    private static ClientVersionBean clientVersion;

    @Before
    public void openPage() throws Exception {
        page = open(ClientContractsDetailPage.class,
            organization.getId(),
            client.getId(),
            clientVersion.getVersion());
    }

    @Test
    public void shouldDisplayCorrectContractInfo() throws Exception {
        page.findEntries(text(organization.getName()),
            text(api.getName()),
            text(apiVersion.getVersion()),
            text(plan.getName()))
            .shouldHaveSize(1);
    }

    @Test
    public void shouldListCorrectNumberOfContracts() throws Exception {
        page.entries().shouldHaveSize(1);
    }

    @Test
    public void shouldDisplayNewContractPageWithCorrectInfoWhenClickOnNewContractButton() throws Exception {
        CreateContractPage contractPage = page.newContract();

        contractPage.clientSelect().selected()
            .shouldHave(text(client.getName()));

        contractPage.clientVersionSelect().selected()
            .shouldHave(text(clientVersion.getVersion()));
    }
}
