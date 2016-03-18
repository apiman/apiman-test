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

import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.base.AbstractApiUITest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.BrowseApiDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.plans.PlanBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({VisualTest.class})
public class BrowseApiDetailIT extends AbstractApiUITest {

    private BrowseApiDetailPage page;

    @Plan(organization = "organization")
    @PlanVersion
    public static PlanBean plan;

    @ApiVersion(api = "api", version = "public", vPlans = "plan")
    public static ApiVersionBean publicApiVersion;

    @ApiVersion(api = "api", version = "nonPublic", isPublic = false)
    public static ApiVersionBean nonPublicApiVersion;

    @ManagedEndpoint("publicApiVersion")
    public static String endpoint;

    @Before
    public void openPage() throws Exception {
        page = open(
            BrowseApiDetailPage.class,
            organization.getId(),
            api.getId(),
            publicApiVersion.getVersion());
    }

    @Test
    public void shouldShowCorrectOrganizationName() throws Exception {
        page.organizationLink()
            .shouldHave(text(organization.getId()));
    }

    @Test
    public void shouldShowCorrectApiName() throws Exception {
        page.apiTitle()
            .shouldHave(text(api.getId()));
    }

    @Test
    public void shouldShowCorrectApiDescription() throws Exception {
        page.apiDescription()
            .shouldHave(text(api.getDescription()));
    }

    @Test
    public void shouldShowCorrectVersion() throws Exception {
        page.apiVersion()
            .shouldHave(text(publicApiVersion.getVersion()));
    }

    @Test
    public void shouldHaveCorrectEndpoint() throws Exception {
        page.endpointTextarea()
            .shouldHave(value(endpoint));
    }

    @Test
    public void shouldNotShowNonPublicInVersionList() throws Exception {
        page.versionSelectorOptions()
            .findBy(text(nonPublicApiVersion.getVersion()))
            .shouldNot(exist);
    }

    @Test
    public void shouldHaveCorrectAvaliablePlan() throws Exception {
        page.entries()
            .shouldHaveSize(1);

        page.entityLink(plan.getName())
            .shouldBe(visible);
        page.entityDescription(plan.getName())
            .shouldHave(text(plan.getDescription()));
    }

    @Test
    public void shouldHaveCreateContractButtonAvailable() throws Exception {
        page.createContractButton(plan.getName())
            .shouldBe(visible);
    }
}
