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

import io.apiman.test.integration.base.AbstractTest;
import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.ManageOrgMembersPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * @author ldimaggi
 *
 * Acceptance test for requirement: APIMAN-48 - Organization UI - Manage Members UI Page
 * Test verifies presence of UI elements that contain expected information as created via REST for: 
 *    /apimanui/api-manager/orgs/{0}/manage-members - ManageOrgMembersListPage.java
 *
 */
@RunWith(ApimanRunner.class)
@Category({VisualTest.class})
public class OrgManageMembersIT extends AbstractTest {

    /**
     * Verify expected presence of user in org members page
     */
    @Test
    public void shouldDisplayCorrectUserInfo() {
        ManageOrgMembersPage theMembersPage = open(ManageOrgMembersPage.class, organization.getName());

        ElementsCollection theCards = theMembersPage.userCards();
        SelenideElement foundCard = theCards.find(text("admin"));

        theMembersPage.userCardDataField(foundCard, "userId").shouldHave(text("admin"));
        theMembersPage.userCardDataField(foundCard, "roles").shouldHave(text("Organization Owner"));
    }

}
