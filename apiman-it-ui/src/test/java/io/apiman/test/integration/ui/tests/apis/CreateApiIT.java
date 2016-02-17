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

package io.apiman.test.integration.ui.tests.apis;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.hasText;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.ApiUtils;
import io.apiman.test.integration.ui.support.selenide.pages.ErrorPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiPage;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author jcechace
 */
@RunWith(ApimanRunner.class)
public class CreateApiIT {

    @Organization
    private static OrganizationBean organization;

    @Api(organization = "organization")
    private static ApiBean existingApi;

    private static ApiBean localApi;

    private static CreateApiPage createApiPage;

    @Before
    public void setUp() throws Exception {
        localApi = ApiUtils.local(organization);
        createApiPage = open(CreateApiPage.class);
    }

    @Test
    public void shouldNotAcceptEmptyName() throws Exception {
        createApiPage
            .organization(organization.getName())
            .version("1.0")
            .createButton().shouldBe(disabled);
    }

    @Test
    public void shouldNotAcceptEmptyVersion() throws Exception {
        createApiPage
            .organization(organization.getName())
            .name(localApi.getName())
            .version("")
            .createButton().shouldBe(disabled);
    }

    @Test
    public void shouldNotAcceptAlreadyUsedName() throws Exception {
        createApi(existingApi);
        ErrorPage errorPage = page(ErrorPage.class);
        errorPage.description().should(hasText("already exist"));
    }

    @Test
    public void shouldSuccessfullyCreateApi() throws Exception {
        createApi(localApi);
        PageAssert.assertApiDetail(localApi);
        BeanAssert.assertApi(localApi);
    }

    public void createApi(ApiBean api) {
        createApiPage
            .organization(api.getOrganization().getName())
            .name(api.getName())
            .description(api.getDescription())
            .version("1.0")
            .create();
    }
}
