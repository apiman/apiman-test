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

package io.apiman.test.integration.ui.tests.organizations;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;

import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: APIMAN-42 - Organization UI - Apis UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    /apimanui/api-manager/orgs/{org}/apis/{api}/{version} - ApiDetailPage.java
 *
 */
@Category({VisualTest.class})
public class OrgApiIT extends AbstractUITest {

    @PlanVersion(plan = "plan")
    public static PlanVersionBean planVersion;

    @ApiVersion(api = "api", vPlans = {"planVersion"})
    public static ApiVersionBean apiVersion;

    /**
     * Verify expected presence of api
     * @throws InterruptedException
     */
    @Test
    public void shouldDisplayCorrectApiInfo() throws InterruptedException {
        ApiDetailPage theApiDetailPage = open(ApiDetailPage.class, organization.getName(), api.getName(), apiVersion
            .getVersion());
        theApiDetailPage.description().shouldHave(text(api.getDescription()));
        theApiDetailPage.name().shouldHave(text(api.getName()));
        theApiDetailPage.version().shouldHave(text(apiVersion.getVersion()));
    }

}
