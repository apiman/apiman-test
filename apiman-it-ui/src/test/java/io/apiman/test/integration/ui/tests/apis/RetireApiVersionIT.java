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

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractApiUITest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jcechace
 */
public class RetireApiVersionIT extends AbstractApiUITest {

    public static final String STATUS_RETIRED = "retired";
    public static final String STATUS_PUBLISHED = "published";

    @ApiVersion(version = "published", api = "api", unique = true)
    private ApiVersionBean publishedVersion;

    @ApiVersion(version = "unpublished", api = "api", publish = false, unique = true)
    private ApiVersionBean unpublishedVersion;

    private static ApiVersions apiVersions;

    @BeforeClass
    public static void setUp() {
        apiVersions = new ApiVersions(api);
    }

    public ResponseSpecification expectedStatus(String status) {
        ResponseSpecification expectedStatus = new ResponseSpecBuilder()
            .expectBody("status", equalToIgnoringCase(status))
            .build();
        return expectedStatus;
    }

    @Test
    public void canRetirePublishedApiVersion() {
        openApiDetailPage(publishedVersion)
            .retire().confirmDialog()
            .status().shouldHave(text(STATUS_RETIRED));
        apiVersions.peek(publishedVersion.getVersion(), expectedStatus(STATUS_RETIRED));
    }

    @Test
    public void canNotRetireApiWhenNotConfirmed() {
        openApiDetailPage(publishedVersion)
            .retire().dismissDialog()
            .status().shouldHave(text(STATUS_PUBLISHED));
        apiVersions.peek(publishedVersion.getVersion(), expectedStatus(STATUS_PUBLISHED));
    }

    @Test
    public void shouldNotShowRetireButtonNotForUnpublishedApiVersion() {
        openApiDetailPage(unpublishedVersion).retireButton().shouldNotBe(visible);
    }

    private static ApiDetailPage openApiDetailPage(ApiVersionBean version) {
        return open(ApiDetailPage.class,
            version.getApi().getOrganization().getId(),
            version.getApi().getId(),
            version.getVersion());
    }
}
