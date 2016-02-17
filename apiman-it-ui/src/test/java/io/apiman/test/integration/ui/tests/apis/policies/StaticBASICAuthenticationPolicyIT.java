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

package io.apiman.test.integration.ui.tests.apis.policies;

import static io.apiman.test.integration.ui.support.assertion.BeanAssert.assertNoPolicies;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.disabled;

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddStaticBASICAuthenticationPolicyPage;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class StaticBASICAuthenticationPolicyIT extends AbstractApiPolicyIT {

    private AddStaticBASICAuthenticationPolicyPage addPolicyPage;

    @Before
    public void setUp() {
        addPolicyPage = policiesDetailPage.addPolicy(AddStaticBASICAuthenticationPolicyPage.class);
        addPolicyPage.authenticationRealm("baz");
    }

    private void addIdentities() {
        addPolicyPage
            .addIdentity("foo", "foo")
            .addIdentity("bar", "bar");
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.BASIC_AUTHENTICATION;
    }

    @Test
    public void canAddPolicy() {
        addIdentities();
        addPolicyPage.addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canCancelConfiguration() {
        addIdentities();
        addPolicyPage
            .cancel(ApiPoliciesDetailPage.class);
        assertNoPolicies(apiVersions);
    }

    @Test
    public void canNotAddPolicyWithoutIdentity() {
        addPolicyPage.addPolicyButton().shouldBe(disabled);

        addIdentities();
        addPolicyPage
            .clearSelectList()
            .addPolicyButton().shouldBe(disabled);
    }

    @Test
    public void canClearIdentities() {
        addIdentities();
        addPolicyPage
            .clearSelectList()
            .listedOptions().shouldBe(empty);
    }

    @Test
    public void canRemoveOneIdentity() {
        addIdentities();
        int size = addPolicyPage.listedOptions().size();

        addPolicyPage
            .removeOption("foo")
            .listedOptions().shouldHaveSize(size - 1);
    }
}
