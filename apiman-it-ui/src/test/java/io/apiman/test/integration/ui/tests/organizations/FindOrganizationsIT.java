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

import static com.codeborne.selenide.Condition.*;

import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.OrgUtils;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.BrowseOrgPage;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jcechace
 */
public class FindOrganizationsIT {

    private static OrganizationBean firstOrgBean;
    private static OrganizationBean secondOrgBean;

    @BeforeClass
    public static void createOrgBean() {
        firstOrgBean = OrgUtils.remote();
        secondOrgBean = OrgUtils.remote();
    }

    @Test
    public void shouldNotListAnyResultBeforeSearch() {
        BrowseOrgPage browseOrgPage = open(BrowseOrgPage.class);

        browseOrgPage.noContentInfo().shouldBe(present);
    }

    @Test
    public void shouldNotFindAnything() {
        BrowseOrgPage browseOrgPage = open(BrowseOrgPage.class)
            .search("ThisOrganizationDoesNotExistForSure");

        browseOrgPage.noContentInfo().shouldBe(present);
    }

    @Test
    public void shouldFindSingleResult() {
        BrowseOrgPage browseOrgPage = open(BrowseOrgPage.class)
            .search(firstOrgBean.getName());

        browseOrgPage.noContentInfo().shouldNotBe(visible);
        browseOrgPage.resultEntries().shouldHaveSize(1);
    }

    @Test
    public void shouldFindMultipleResults() {
        BrowseOrgPage browseOrgPage = open(BrowseOrgPage.class)
            .search(OrgUtils.TEST_ORG_NAME_BASE);

        browseOrgPage.noContentInfo().shouldNotBe(visible);
        browseOrgPage.resultEntries()
            .filterBy(or("name equal to", text(firstOrgBean.getName()), text(secondOrgBean.getName())))
            .shouldHaveSize(2);
    }

    @Test
    public void shouldLandOnCorrectPageWhenClickOnResult() {
        BrowseOrgPage browseOrgPage = open(BrowseOrgPage.class)
            .search(firstOrgBean.getName());

        browseOrgPage.openResult(firstOrgBean.getName());

        PageAssert.assertBrowseOrganizationDetail(firstOrgBean);
    }
}
