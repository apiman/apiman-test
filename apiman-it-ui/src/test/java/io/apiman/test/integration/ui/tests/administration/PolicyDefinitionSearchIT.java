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

package io.apiman.test.integration.ui.tests.administration;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.*;

import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.PolicyDefinitionUtils;
import io.apiman.test.integration.ui.support.selenide.pages.administration.policies.PolicyDefsAdminPage;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jrumanov
 */
public class PolicyDefinitionSearchIT {

    private static PolicyDefinitionBean defBean;
    private static PolicyDefsAdminPage policyPage;

    private static final String AUTH_POLICY = "Authorization Policy";
    private static final String BASIC_AUTHENTICATION_POLICY = "BASIC Authentication Policy";
    private static final String IP_BLACKLIST_POLICY = "IP Blacklist Policy";
    private static final String IP_WHITELIST_POLICY = "IP Whitelist Policy";
    private static final String IGNORED_RESOURCES_POLICY = "Ignored Resources Policy";
    private static final String RATE_LIMITING_POLICY = "Rate Limiting Policy";

    @BeforeClass
    public static void createDefBean() {
        defBean = PolicyDefinitionUtils.remote();
    }

    @Before
    public void openPage() {
        policyPage = open(PolicyDefsAdminPage.class);
    }

    @Test
    public void shouldNotShowResultsWithNonexistingFilter() {
        policyPage.filter("someJibberJabber#$");
        policyPage.noContentInfo().shouldBe(present);
    }

    @Test
    public void shouldShowBuiltInPoliciesBeforeSearch() {
        policyPage.noContentInfo().shouldNotBe(visible);
        policyPage.entries().filterBy(and("name equal to",
                text(AUTH_POLICY),
                text(BASIC_AUTHENTICATION_POLICY),
                text(IP_BLACKLIST_POLICY),
                text(IP_WHITELIST_POLICY),
                text(IGNORED_RESOURCES_POLICY),
                text(RATE_LIMITING_POLICY)));
    }

    @Test
    public void shouldEraseSearchButtonOnNonexistingFilter() {
        policyPage.filter("someJibberJabber#$");
        policyPage.filterInput().shouldHave(value("someJibberJabber#$"));
        policyPage.clearFilter();
        policyPage.filterInput().shouldBe(empty);
    }

    @Test
    public void shouldCreateResultLinkLeadingToCorrectPage() {
        policyPage.filter(defBean.getId());
        policyPage.openEntity(defBean.getId());
        PageAssert.assertPolicyDefinitionsDetail(defBean); 
    }

    @Test
    public void shouldShowDefinitions() {
        policyPage.filter(defBean.getName());
        policyPage.entries().shouldHaveSize(1);
    }
}
