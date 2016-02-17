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

package io.apiman.test.integration.ui.support.selenide.layouts;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.pages.apis.UserApisListPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.UserClientsListPage;
import io.apiman.test.integration.ui.support.selenide.pages.users.UserOrgsListPage;

/**
 * This page contains common elements for pages that are located on: /api-manager/users/{user}/*
 *
 * @author jkaspar
 */
@Layout("/users/{id}/*")
public class UserEntitiesListPage<P> extends AbstractListPage<P> {

    // Tabs

    /**
     * Activates organization tab
     * @return UserOrganizationOverviewPage page object
     */
    public UserOrgsListPage organizations() {
        $("#tab-orgs").click();
        return page(UserOrgsListPage.class);
    }

    /**
     * Activates clients tab
     * @return UserClientsListPage page object
     */
    public UserClientsListPage clients() {
        $("#tab-clients").click();
        return page(UserClientsListPage.class);
    }

    /**
     * Activates apis tab
     * @return UserApisListPage page object
     */
    public UserApisListPage apis() {
        $("#tab-apis").click();
        return page(UserApisListPage.class);
    }
}
