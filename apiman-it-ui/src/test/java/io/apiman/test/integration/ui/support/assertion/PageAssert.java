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

package io.apiman.test.integration.ui.support.assertion;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.layouts.OrgEntitiesListPage;
import io.apiman.test.integration.ui.support.selenide.pages.administration.policies.EditPolicyPage;
import io.apiman.test.integration.ui.support.selenide.pages.administration.policies.PolicyDefsAdminPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.BrowseApiDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.BrowseOrgDetailPage;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;

/**
 * @author jcechace
 */
public class PageAssert {

    /**
     * Assert that information on OrgEntitiesListPage matches given bean
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name
     *
     * @param expected expected bean
     */
    public static void assertUserOrganization(OrganizationBean expected) {
        OrgEntitiesListPage page = page(OrgEntitiesListPage.class);
        page.header().shouldHave(text(expected.getName()));
    }

    /**
     * Assert that information on BrowseOrgDetailPage matches given bean
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, description
     *
     * @param expected expected bean
     */
    public static void assertBrowseOrganizationDetail(OrganizationBean expected) {
        BrowseOrgDetailPage page = page(BrowseOrgDetailPage.class);
        page.orgTitle().shouldHave(text(expected.getName()));
        page.orgDescription().shouldBe(text(expected.getDescription()));
    }

    /**
     * Assert that information on ApiDetailPage matches given bean
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, description
     *
     * @param expected expected bean
     */
    public static void assertApiDetail(ApiBean expected) {
        ApiDetailPage page = page(ApiDetailPage.class);
        page.name().shouldHave(text(expected.getName()));
        page.description().shouldHave(text(expected.getDescription()));
    }


    /**
     * Assert that information on ApiDetailPage matches given bean
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, description
     *
     * @param expected expected bean
     */
    public static void assertClientDetail(ClientBean expected) {
        ClientDetailPage page = page(ClientDetailPage.class);
        page.name().shouldHave(text(expected.getName()));
        page.description().shouldHave(text(expected.getDescription()));
    }

    /**
     * Assert that information in Policy Definition matches with the JSON
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, id
     *
     * @author jrumanov
     * @param expected expected bean
     */
    public static void assertPolicyDefinitionsDetail(PolicyDefinitionBean expected) {
        EditPolicyPage page = open(PolicyDefsAdminPage.class).openEntity(expected.getName());
        page.jsonTextField()
            .shouldHave(text("\"name\": \"" + expected.getName()))
            .shouldHave(text("\"id\": \"" + expected.getId()));
    }

    /**
     * Assert that information on BrowseApiDetailPage matches given bean
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, description
     *
     * @param expected expected bean
     */
    public static void assertBrowseApiDetail(ApiBean expected) {
        BrowseApiDetailPage page = page(BrowseApiDetailPage.class);
        page.apiTitle().shouldHave(text(expected.getName()));
        page.apiDescription().shouldBe(text(expected.getDescription()));
    }
}

