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

import static com.codeborne.selenide.Condition.disabled;

import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.OrgUtils;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.CreateOrgPage;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jcechace
 */
public class CreateOrganizationIT extends AbstractUITest {

    private OrganizationBean orgBean;
    private CreateOrgPage createOrgPage;

    @Before
    public void createOrgBean() {
        this.orgBean = OrgUtils.local();
        this.createOrgPage = open(CreateOrgPage.class);
    }

    @Test
    public void canCreateOrganization() {
        createOrgPage
            .name(orgBean.getName())
            .description(orgBean.getDescription())
            .create();

        PageAssert.assertUserOrganization(orgBean);
        BeanAssert.assertOrganization(orgBean);
    }

    @Test
    public void canNotSubmitFormWhenEmptyName() {
        createOrgPage.description(orgBean.getDescription());
        createOrgPage.createButton().shouldBe(disabled);
    }

    @Test
    public void canNotSubmitFormWhenEmptyDescription() {
        createOrgPage.name(orgBean.getName());
        createOrgPage.createButton().shouldNotBe(disabled);
    }
}
