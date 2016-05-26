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

import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.selenide.base.AbstractSimpleUITest;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgPlansListPage;

import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import com.codeborne.selenide.Condition;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jsmolar.
 */
public class DeleteOrganizationIT extends AbstractSimpleUITest {

    @Organization
    private OrganizationBean organization;

    @Organization()
    public static OrganizationBean orgWithApi;

    @Api(organization = "orgWithApi")
    public static ApiBean api;

    @ApiVersion(api = "api")
    private static ApiVersionBean apiVersion;

    private OrgPlansListPage thePlansPage;

    @Before
    public void setUp() {
        thePlansPage = open(OrgPlansListPage.class, organization.getName());
        thePlansPage.deleteButton().click();
    }

    @Test
    public void shouldDeleteOrganization() {
        thePlansPage.confirmOrgName(organization.getName());
        thePlansPage.confirmOrgNameButton().click();
        BeanAssert.assertOrganizationNotPresent(organization);
    }

    @Test
    public void couldNotDeleteWithWrongName() {
        thePlansPage.confirmOrgName("WrongOrgName");
        thePlansPage.confirmOrgNameButton().shouldBe(Condition.disabled);
    }

    @Test
    public void shouldNotDeleteOrgWithPublishedApi() {
        thePlansPage.confirmOrgName(orgWithApi.getName());
        thePlansPage.confirmOrgNameButton().click();
        BeanAssert.assertOrganization(organization);
    }
}
