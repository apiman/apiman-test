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

import static com.codeborne.selenide.Condition.visible;
import static org.junit.Assert.assertEquals;

import io.apiman.test.integration.Suite;
import io.apiman.test.integration.SuiteProperties;
import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.ui.support.selenide.base.clients.AbstractBreakContractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientContractsDetailPage;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.summary.ContractSummaryBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class BreakContractIT extends AbstractBreakContractUITest {

    private static ClientContractsDetailPage page;

    private static void openPage(ClientVersionBean clientVersion) {
        page = open(ClientContractsDetailPage.class,
            clientVersion.getClient().getOrganization().getId(),
            clientVersion.getClient().getId(),
            clientVersion.getVersion());
    }

    @Test
    public void canBreakContract() throws Exception {
        openPage(unregisteredClientVersion);
        page.breakContract(secondApiVersion.getVersion());

        getContract(unregisteredContractBean, expectedStatusCode(404));
    }

    @Test
    public void shouldRemoveOnlySingleContract() throws Exception {
        List<ContractSummaryBean> contractsBefore = getContracts(unregisteredClientVersion);

        openPage(unregisteredClientVersion);
        page.breakContract(secondApiVersion.getVersion());

        List<ContractSummaryBean> contractsAfter = getContracts(unregisteredClientVersion);
        assertEquals("Unexpected number of contracts", contractsBefore.size() - 1, contractsAfter.size());
    }

    @Test
    public void canBreakAllContracts() throws Exception {
        openPage(unregisteredClientVersion);
        page.breakAllContracts();

        Suite.waitForAction();
        List<ContractSummaryBean> contracts = getContracts(unregisteredClientVersion);
        assertEquals("Unexpected number of contracts", 0, contracts.size());
    }

    // TODO: Should we remove this test?
    @Test
    @Ignore
    public void canNotBreakContractOnRegisteredClientVersion() throws Exception {
        openPage(registeredClientVersion);
        page.breakContractButton(AbstractApiTest.api.getName()).shouldNotBe(visible);
    }

    // TODO: Should we remove this test?
    @Test
    @Ignore
    public void canNotBreakAllContractsOnRegisteredClientVersion() throws Exception {
        openPage(registeredClientVersion);
        page.breakAllContractsButton().shouldNotBe(visible);
    }
}
