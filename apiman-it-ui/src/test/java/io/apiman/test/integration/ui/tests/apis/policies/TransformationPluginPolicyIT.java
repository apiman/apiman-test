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
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddTransformationPolicyPage;
import org.junit.Before;
import org.junit.Test;

/**
 * @author opontes
 */
@Plugin(artifactId = "apiman-plugins-transformation-policy")
public class TransformationPluginPolicyIT extends AbstractApiPolicyIT {

    private AddTransformationPolicyPage addPolicyPage;

    @Before
    public void openPage(){
        addPolicyPage = policiesDetailPage.addPolicy(AddTransformationPolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.TRANSFORMATION_POLICY;
    }

    @Test
    public void canAddPolicy(){
        addPolicyPage
                .configure("XML", "XML")
                .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canCancelPolicy(){
        addPolicyPage
                .configure("XML", "XML")
                .cancel(ApiPoliciesDetailPage.class);
        BeanAssert.assertNoPolicies(apiVersions);
    }
}
