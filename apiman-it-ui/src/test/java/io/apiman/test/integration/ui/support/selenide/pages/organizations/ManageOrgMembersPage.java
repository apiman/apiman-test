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

package io.apiman.test.integration.ui.support.selenide.pages.organizations;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar, ldimaggi
 */
@PageLocation("/api-manager/orgs/{0}/manage-members")
public class ManageOrgMembersPage extends AbstractPage {

    /**
     * Manage Members button
     * @return element
     */
    public SelenideElement addMemberButton() {
        return $("a[data-field='toAddMember']");
    }

    /**
     * Click to Manage Members button
     * @return CreateClientPage
     */
    public OrgNewMemberPage addMember() {
        addMemberButton().click();
        return page(OrgNewMemberPage.class);
    }

    /**
     * Return root of all apiman-cards
     * @return SelenideElement
     */
    public SelenideElement apimanCardsRoot () {
        return $("div.container-fluid.apiman-cards");
    } 
    
    /**
     * Return the collection of all apiman user cards
     * @return ElementsCollection
     */
    public ElementsCollection userCards() {
        return apimanCardsRoot().$$("apiman-user-card");
    }

    /**
     * Return total count of all apiman-cards
     * @return counter
     */
    public int apimanCardCount () {
        return userCards().size();
    }

    /**
     * Return the selected data field element
     * @param startPos
     * @param dataField
     * @return SelenideElement
     */
    public SelenideElement userCardDataField(SelenideElement startPos, String dataField) {
        return startPos.$("[data-field='" + dataField + "']");
    }   

}
