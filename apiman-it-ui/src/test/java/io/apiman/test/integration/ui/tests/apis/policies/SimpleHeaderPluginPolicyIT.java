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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddSimpleHeaderPolicyPage;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by pstanko.
 * @author pstanko
 */
@Plugin(artifactId = "apiman-plugins-simple-header-policy")
public class SimpleHeaderPluginPolicyIT extends AbstractApiPolicyIT {

    private AddSimpleHeaderPolicyPage addPolicyPage;

    @Before
    public void openPage(){
        addPolicyPage = policiesDetailPage.addPolicy(AddSimpleHeaderPolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.SIMPLE_HEADER_POLICY;
    }

    @Test
    public void shouldAddRequestPolicy(){
        addPolicyPage
            .addHeader("X-Request", "Request value",
                AddSimpleHeaderPolicyPage.ValueType.String, AddSimpleHeaderPolicyPage.ApplyTo.Request, true)
            .addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
        assertThat(addPolicyPage.getAddHeaderCount(), equalTo(1));
    }

    @Test
    public void shouldStripValueByRegexPolicy(){
        addPolicyPage
            .stripHeader(AddSimpleHeaderPolicyPage.HeaderType.Value, AddSimpleHeaderPolicyPage.WithMatcher.Regex, "ahoj")
            .addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
        assertThat(addPolicyPage.getStripHeaderCount(), equalTo(1));
    }




}
