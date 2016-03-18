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
import static com.codeborne.selenide.Condition.text;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiDetailPage;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class PublishApiVersionIT extends AbstractUITest {

    @Organization
    private static OrganizationBean organization;

    @Api(organization = "organization")
    private static ApiBean api;

    @Plan(organization = "organization")
    @PlanVersion
    private static PlanVersionBean planVersion;

    @ApiVersion(
        version = "public",
        api = "api",
        publish = false,
        isPublic = true)
    private static ApiVersionBean publicApiVersion;

    @ApiVersion(
        version = "with-plan",
        api = "api",
        publish = false,
        isPublic = false,
        vPlans = {"planVersion"})
    private static ApiVersionBean apiVersionWithPlan;

    @ApiVersion(
        version = "non-public-without-plan",
        api = "api",
        publish = false,
        isPublic = false,
        vPlans = {})
    private static ApiVersionBean nonPublicApiVersionWithoutPlan;

    private static ResponseSpecification expectedPublished;
    private static ApiVersions apiVersions;

    @BeforeClass
    public static void setUp() {
        apiVersions = new ApiVersions(api);
        expectedPublished = new ResponseSpecBuilder()
            .expectBody("status", equalTo("Published"))
            .expectBody("publishedOn", notNullValue())
            .build();
    }

    @Test
    public void canPublishPublicApiVersion() throws Exception {
        openApiDetailPage(publicApiVersion)
            .publish()
            .status().shouldHave(text("published"));
        apiVersions.peek(publicApiVersion.getVersion(), expectedPublished);
    }

    @Test
    public void canPublishApiVersionWithPlan() throws Exception {
        openApiDetailPage(apiVersionWithPlan)
            .publish()
            .status().shouldHave(text("published"));
        apiVersions.peek(apiVersionWithPlan.getVersion(), expectedPublished);
    }

    @Test
    public void canNotPublishNonPublicApiVersionWithoutPlan() throws Exception {
        openApiDetailPage(nonPublicApiVersionWithoutPlan).publishButton().shouldBe(disabled);
    }

    private static ApiDetailPage openApiDetailPage(ApiVersionBean apiVersion) throws Exception {
        return open(ApiDetailPage.class,
            apiVersion.getApi().getOrganization().getId(),
            apiVersion.getApi().getId(),
            apiVersion.getVersion());
    }
}
