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

import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.ApiUtils;
import io.apiman.test.integration.ui.support.beanutils.OrgUtils;
import io.apiman.test.integration.ui.support.selenide.pages.apis.BrowseApiPage;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author opontes
 */
public class FindApisIT {

    private static ApiBean firstApiBean;
    private static ApiBean secondApiBean;

    @BeforeClass
    public static void createApiBean(){
        OrganizationBean orgBean = OrgUtils.remote();
        firstApiBean = ApiUtils.remote(orgBean);
        secondApiBean = ApiUtils.remote(orgBean);
    }

    @Test
    public void shouldNotListAnyResultBeforeSearch() {
        BrowseApiPage browseApiPage = open(BrowseApiPage.class, "");

        browseApiPage.noContentInfo().shouldBe(present);
    }

    @Test
    public void shouldNotFindAnything() {
        BrowseApiPage browseApiPage = open(BrowseApiPage.class)
                .search("ThisOrganizationDoesNotExistForSure");

        browseApiPage.noContentInfo().shouldBe(present);
    }

    @Test
    public void shouldFindSingleResult() {
        BrowseApiPage browseApiPage = open(BrowseApiPage.class)
                .search(firstApiBean.getName());

        browseApiPage.noContentInfo().shouldNotBe(visible);
        browseApiPage.resultEntries().shouldHaveSize(1);
    }

    @Test
    public void shouldFindMultipleResults() {
        BrowseApiPage browseOrgPage = open(BrowseApiPage.class)
                .search(ApiUtils.TEST_API_NAME_BASE);

        browseOrgPage.noContentInfo().shouldNotBe(visible);
        browseOrgPage.resultEntries()
                .filterBy(or("name equal to", text(firstApiBean.getName()), text(secondApiBean.getName())))
                .shouldHaveSize(2);
    }

    @Test
    public void shouldLandOnCorrectPageWhenClickOnResult() {
        BrowseApiPage browseOrgPage = open(BrowseApiPage.class)
                .search(firstApiBean.getName());

        browseOrgPage.openResult(firstApiBean.getName());

        PageAssert.assertBrowseApiDetail(firstApiBean);
    }
}
