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

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddCachingPolicyPage;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class CachingPolicyIT extends AbstractApiPolicyIT {

    private AddCachingPolicyPage addPolicyPage;

    @Before
    public void openPage() {
        addPolicyPage = policiesDetailPage.addPolicy(AddCachingPolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.CACHING_POLICY;
    }

    @Test
    public void canAddPolicy() {
        addPolicyPage
            .timeToLive(60)
            .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canCancelConfiguration() {
        addPolicyPage
            .timeToLive(60)
            .cancel(ApiPoliciesDetailPage.class);
        BeanAssert.assertNoPolicies(apiVersions);
    }
}
