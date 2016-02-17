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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.test.integration.ui.support.selenide.pages.ErrorPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiVersionPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.AbstractApiDetailPage;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author jkaspar
 */
@RunWith(ApimanRunner.class)
public class CreateApiVersionIT {

    // Test Attributes
    private static final String EXISTING_VERSION = "existing-version";
    private static final String NEW_VERSION = "new-version";
    private static final String COPIED_VERSION = "copied-version";

    private static CreateApiVersionPage createApiVersionPage;
    private static ApiVersions apiVersions;

    //Apiman Configuration
    @Organization
    private static OrganizationBean organization;

    @Api(organization = "organization")
    private static ApiBean api;

    @ApiVersion(version = EXISTING_VERSION, api = "api")
    private static ApiVersionBean existingApiVersion;

    @BeforeClass
    public static void setUpClass() throws Exception {
        apiVersions = new ApiVersions(api);
    }

    @Before
    public void setUp() throws Exception {
        createApiVersionPage = open(
            CreateApiVersionPage.class,
            organization.getId(),
            api.getId(),
            EXISTING_VERSION);
    }

    @Test
    public void shouldNotCreateEmptyVersion() throws Exception {
        createApiVersionPage
            .version("")
            .createButton().shouldBe(disabled);
    }

    @Test
    public void shouldFailOnAlreadyUsedVersion() throws Exception {
        createApiVersionPage
            .version(EXISTING_VERSION)
            .create();

        ErrorPage errorPage = page(ErrorPage.class);
        errorPage.description().shouldHave(hasText("already exist"));
    }

    @Test
    public void shouldCreateNewApiVersionSuccessfully() throws Exception {
        AbstractApiDetailPage apiDetailPage = createApiVersionPage.version(NEW_VERSION).clone(false).create();

        apiDetailPage.versionSelectorButton().shouldHave(text(NEW_VERSION));
        apiVersions.peek(NEW_VERSION);
    }

    @Test
    public void shoulcCreateApiVersionByCopySuccessfully() throws Exception {
        AbstractApiDetailPage apiDetailPage = createApiVersionPage.version(COPIED_VERSION).clone(true).create();

        apiDetailPage.versionSelectorButton().shouldHave(text(COPIED_VERSION));
        apiVersions.peek(COPIED_VERSION);
    }
}
