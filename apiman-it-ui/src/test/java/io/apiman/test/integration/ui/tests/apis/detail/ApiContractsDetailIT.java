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

package io.apiman.test.integration.ui.tests.apis.detail;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;

import io.apiman.test.integration.base.AbstractClientTest;
import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiContractsDetailPage;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({VisualTest.class})
public class ApiContractsDetailIT extends AbstractClientTest {

    private static ApiContractsDetailPage page;

    @ClientVersion(client = "client",
        contracts = @Contract(vApi = "apiVersion", vPlan = "planVersion"))
    private static ClientVersionBean clientVersion;

    @Before
    public void openPage() throws Exception {
        page = open(ApiContractsDetailPage.class,
            organization.getId(),
            api.getId(),
            apiVersion.getVersion());
    }

    @Test
    public void shouldDisplayCorrectParametersOfContract() throws Exception {
        page.contractsItems(text(organization.getName()),
            text(client.getName()),
            text(clientVersion.getVersion()),
            text(plan.getName()))
            .shouldHaveSize(1);
    }

    @Test
    public void shouldDisplayCorrectNumberOfContracts() throws Exception {
        page.contractsItems().shouldHaveSize(1);
    }
}
