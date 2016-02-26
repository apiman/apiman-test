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
import static com.codeborne.selenide.Condition.value;

import io.apiman.test.integration.base.AbstractClientTest;
import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientApisDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({VisualTest.class})
public class ClientApisDetailIT extends AbstractClientTest {

    @ApiVersion(api = "api", vPlans = {"planVersion"}, version = "2.0")
    public static ApiVersionBean secondApiVersion;

    @ClientVersion(client = "client", contracts = {
        @Contract(vApi = "apiVersion", vPlan = "planVersion"),
        @Contract(vApi = "secondApiVersion", vPlan = "planVersion")})
    private static ClientVersionBean clientVersion;

    @ApiKey(vPlan = "planVersion", vApi = "apiVersion", vClient = "clientVersion")
    private static String apiKey;

    @ApiKey(vPlan = "planVersion", vApi = "secondApiVersion", vClient = "clientVersion")
    private static String secodnApiKey;

    private ClientApisDetailPage page;

    @Before
    public void setUp() throws Exception {
        page = open(ClientApisDetailPage.class,
            organization.getId(),
            client.getId(),
            clientVersion.getVersion());
    }

    @Test
    public void shouldListCorrectNumberOfApis() throws Exception {
        page.allApis().shouldHaveSize(2);
    }

    @Test
    public void shouldDisplayCorrectApiInformation() throws Exception {
        page.findApis(text(organization.getName()),
            text(api.getName()),
            text(apiVersion.getVersion()),
            text(plan.getName()))
            .shouldHaveSize(1);
    }

    @Test
    public void shouldDisplayCorrectApiKeys() throws Exception {
        page.apiKeyInput(text(organization.getName()), text(apiVersion.getVersion()))
            .shouldHave(value(apiKey));

        page.apiKeyInput(text(organization.getName()), text(secondApiVersion.getVersion()))
            .shouldHave(value(secodnApiKey));
    }

    @Test
    public void shouldDisplayCorrectHowToInvokeQueryParameter() throws Exception {
        page.queryParameterTextarea(text(organization.getName()), text(apiVersion.getVersion()))
            .shouldHave(value(addApiKeyParameter(endpoint, apiKey)));
    }

    @Test
    public void shouldDisplayCorrectHowToInvokeHttpHeader() throws Exception {
        page.httpHeaderTextarea(text(organization.getName()), text(apiVersion.getVersion()))
            .shouldHave(value("X-API-Key: " + apiKey));
    }
}
