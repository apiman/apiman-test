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

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddIgnoredResourcePolicyPage;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO: provide better policy verification
 * @author jcechace
 */
public class IgnoredResourcePolicyIT extends AbstractApiPolicyIT {

    public static final String IGNORED_PATH_FOO = "/foo";
    public static final String IGNORED_PATH_BAR = "/bar";

    private AddIgnoredResourcePolicyPage addPolicyPage;

    @Before
    public void openPage() {
        addPolicyPage = policiesDetailPage.addPolicy(AddIgnoredResourcePolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.IGNORED_RESOURCE;
    }

    protected void addStartingConfiguration() {
        addPolicyPage
            .ignorePath(IGNORED_PATH_FOO, "*")
            .ignorePath(IGNORED_PATH_BAR, "POST");
    }

    @Test
    public void canAddPolicyWithMultipleIgnoredPaths() {
        addStartingConfiguration();
        addPolicyPage
            .listedRules().shouldHaveSize(2);
        addPolicyPage
            .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canRemovePathFromList() {
        addStartingConfiguration();
        addPolicyPage
            .removeRule(IGNORED_PATH_FOO)
            .listedRules().shouldHaveSize(1);
        addPolicyPage.addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canCancelConfiguration() {
        addStartingConfiguration();
        addPolicyPage.cancel(ApiPoliciesDetailPage.class);
        assertNoPolicies(apiVersions);
    }
}
