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

package io.apiman.test.integration.ui.support.selenide.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.BrowseApiPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.UserApisListPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateClientPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.UserClientsListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.BrowseOrgPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.CreateOrgPage;
import io.apiman.test.integration.ui.support.selenide.pages.users.UserOrgsListPage;

/**
 * This class represent the main page of Apiman UI dashboard
 *
 * @author jcechace
 */
@PageLocation("/api-manager/dash")
public class HomePage extends AbstractPage {

    // Organizations

    /**
     * Follows the link to new organization page
     * @return CreateOrgPage page object
     */
    public CreateOrgPage createOrganization() {
        $("#org-new").click();
        return page(CreateOrgPage.class);
    }

    /**
     * Follows the link to search organizations page
     * @return BrowseOrgPage page object
     */
    public BrowseOrgPage findOrganization() {
        $("#org-browse").click();
        return page(BrowseOrgPage.class);
    }

    /**
     * Follows the link to my organizations
     * @return UserOrganizationOverviewPage page object
     */
    public UserOrgsListPage myOrganizations() {
        $("#org-my-orgs").click();
        return page(UserOrgsListPage.class);
    }

    // Apis

    /**
     * Follows the link to new api page
     * @return CreateApiPage page object
     */
    public CreateApiPage createApi() {
        $("#api-new").click();
        return page(CreateApiPage.class);
    }

    /**
     * Follows the link to search apis page
     * @return BrowseApiPage page object
     */
    public BrowseApiPage findApi() {
        $("#apis-consume").click();
        return page(BrowseApiPage.class);
    }

    /**
     * Follows the link to my apis
     * @return UserApisListPage page object
     */
    public UserApisListPage myApis() {
        $("#apis-manage").click();
        return page(UserApisListPage.class);
    }

    // Client Apps

    /**
     * Follows the link to new client page
     * @return CreateClientPage page object
     */
    public CreateClientPage createClient() {
        $("#clients-new").click();
        return page(CreateClientPage.class);
    }

    /**
     * Follows the link to my clients
     * @return UserClientsListPage page object
     */
    public UserClientsListPage myClients() {
        $("#clients-manage").click();
        return page(UserClientsListPage.class);
    }
}
