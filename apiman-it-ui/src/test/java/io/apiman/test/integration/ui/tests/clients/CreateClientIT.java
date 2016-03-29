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

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.hasText;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.ApiUtils;
import io.apiman.test.integration.ui.support.beanutils.ClientUtils;
import io.apiman.test.integration.ui.support.selenide.base.AbstractSimpleUITest;
import io.apiman.test.integration.ui.support.selenide.pages.ErrorPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateClientPage;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class CreateClientIT extends AbstractSimpleUITest {

    @Organization
    private static OrganizationBean organization;

    @Client(organization = "organization")
    private static ClientBean existingClient;

    private static ClientBean localClient;

    private static CreateClientPage createClientPage;

    @Before
    public void setUp() throws Exception {
        localClient = ClientUtils.local(organization);
        createClientPage = open(CreateClientPage.class);
    }

    @Test
    public void shouldNotAcceptEmptyName() throws Exception {
        createClientPage
            .organization(organization.getName())
            .version("1.0")
            .createButton().shouldBe(disabled);
    }

    @Test
    public void shouldNotAcceptEmptyVersion() throws Exception {
        createClientPage
            .organization(organization.getName())
            .name(localClient.getName())
            .version("")
            .createButton().shouldBe(disabled);
    }

    @Test
    public void shouldSuccessfullyCreateApi() throws Exception {
        createClient(localClient);
        PageAssert.assertClientDetail(localClient);
        BeanAssert.assertClient(localClient);
    }

    @Test
    public void shouldNotAcceptAlreadyUsedName() throws Exception {
        createClient(existingClient);
        ErrorPage errorPage = page(ErrorPage.class);
        errorPage.description().should(hasText("already exist"));
    }

    public void createClient(ClientBean client) {
        createClientPage
            .organization(client.getOrganization().getName())
            .name(client.getName())
            .description(client.getDescription())
            .version("1.0")
            .create();
    }


}
