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

import static com.codeborne.selenide.Condition.exist;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import io.apiman.test.integration.Suite;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.entity.APIs;
import io.apiman.test.integration.ui.support.selenide.base.AbstractSimpleUITest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.introduce.ImportApiStep1Page;
import io.apiman.test.integration.ui.support.selenide.pages.apis.introduce.ImportApiStep2Page;
import io.apiman.test.integration.ui.support.selenide.pages.apis.introduce.ImportApiStep4Page;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;
import io.apiman.manager.api.beans.summary.ApiSummaryBean;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class ImportApisIT extends AbstractSimpleUITest {

    @Organization
    private OrganizationBean organization;

    @Plan(organization = "organization")
    @PlanVersion
    private PlanVersionBean plan;

    private ImportApiStep2Page page;

    private final String API_NAME_1 = "Foo";
    private final String API_NAME_2 = "Bar";
    private final String TWO_APIS_QUERY = "Baz";

    @Before
    public void setUp() throws Exception {
        page = open(ImportApiStep1Page.class, organization.getId()).next()
            .importFrom("Api Catalog");
    }

    @Test
    public void canSearch() throws Exception {
        page.search(TWO_APIS_QUERY).searchResults().shouldHaveSize(2);
    }

    @Test
    public void canSelectApi() throws Exception {
        page.selectedApis().shouldHaveSize(0);
        page.search(API_NAME_1).
            selectApi(API_NAME_1).selectedApis().shouldHaveSize(1);
    }

    @Test
    public void shouldRemoveSelectedApi() throws Exception {
        page.search(API_NAME_1).selectApi(API_NAME_1)
            .selectedApis().shouldHaveSize(1);
        page.removeApi(API_NAME_1)
            .selectedApis().shouldHaveSize(0);
    }

    @Test
    public void shouldDisplayAvailablePlan() throws Exception {
        page.search(API_NAME_1).selectApi(API_NAME_1)
            .next()
            .planContainer(plan.getPlan().getName()).should(exist);
    }

    @Test
    public void confirmImportDisplaysSelectedApis() throws Exception {
        addTwoApisAndGoToTheLastStep()
            .apis().shouldHaveSize(2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canImportApis() throws Exception {
        addTwoApisAndGoToTheLastStep()
            .importApis();
        Suite.waitForAction();

        List<ApiSummaryBean> apis = new APIs(organization).fetchAll();

        assertThat(apis, hasItems(
            hasProperty("name", is(API_NAME_1)),
            hasProperty("name", is(API_NAME_2))
        ));
    }

    private ImportApiStep4Page addTwoApisAndGoToTheLastStep() {
        return page.search(API_NAME_1).selectApi(API_NAME_1)
            .search(API_NAME_2).selectApi(API_NAME_2).next()
            .publicApis(true).next();
    }
}
