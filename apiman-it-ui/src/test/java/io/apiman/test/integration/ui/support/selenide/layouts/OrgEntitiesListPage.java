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
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgActivityListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgApisListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgClientsListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgMembersListPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgPlansListPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@Layout("/orgs/{id}/*")
public class OrgEntitiesListPage<P> extends AbstractListPage<P> {

    /**
     * Organization description
     * @return element
     */
    public SelenideElement orgDescription() {
        return $("#descriptionWrapper");
    }

    // Tabs

    /**
     * Activates plans tab
     * @return OrgPlansListPage page object
     */
    public OrgPlansListPage plans() {
        $("#tab-plans").click();
        return page(OrgPlansListPage.class);
    }

    /**
     * Activates apis tab
     * @return OrgApisListPage page object
     */
    public OrgApisListPage apis() {
        $("#tab-apis").click();
        return page(OrgApisListPage.class);
    }

    /**
     * Activates clients tab
     * @return OrgClientsListPage page object
     */
    public OrgClientsListPage clients() {
        $("#tab-clients").click();
        return page(OrgClientsListPage.class);
    }

    /**
     * Activates members tab
     * @return OrgMembersListPage page object
     */
    public OrgMembersListPage members() {
        $("#tab-members").click();
        return page(OrgMembersListPage.class);
    }
    
    /**
     * Activates activity tab
     * @return OrgPlansListPage page object
     */
    public OrgActivityListPage activity() {
        $("#tab-activity").click();
        return page(OrgActivityListPage.class);
    }
}
