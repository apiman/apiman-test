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
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgActivityListPage;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: APIMAN-46 - Organization UI - Audit/Activity UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    apimanui/api-manager/orgs/{0}/activity - OrgActivityListPage.java
 *
 */
@Category({VisualTest.class})
public class OrgActivityIT extends AbstractUITest {

    /**
     * Verify expected presence of user, plan, app, api entries in organization activity log
     */
    @Test
    public void shouldListCorrectActivities() {
        OrgActivityListPage theActivityPage = open(OrgActivityListPage.class, organization.getName());

        theActivityPage.entriesContainer().shouldHave(
            text("admin created organization " + organization.getName())
        );
        theActivityPage.entriesContainer().shouldHave(
            text("admin created plan " + organization.getName() + " / " + plan.getName())
        );
        theActivityPage.entriesContainer().shouldHave(
            text("admin created api " + organization.getName() + " / " + api.getName())
        );
        theActivityPage.entriesContainer().shouldHave(
            text("admin created client app " + organization.getName() + " / " + client.getName())
        );
    }

}
