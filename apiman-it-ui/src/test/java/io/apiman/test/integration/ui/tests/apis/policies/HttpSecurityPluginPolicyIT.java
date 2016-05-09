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
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddHttpSecurityPluginPolicyPage;
import org.junit.Before;
import org.junit.Test;

import static io.apiman.test.integration.ui.support.selenide.pages.policies.AddHttpSecurityPluginPolicyPage.*;

/**
 * Created by jsmolar.
 */
@Plugin(artifactId = "apiman-plugins-simple-header-policy")
public class HttpSecurityPluginPolicyIT extends AbstractApiPolicyIT {

    private AddHttpSecurityPluginPolicyPage addPolicyPage;

    @Before
    public void openPage() {
        addPolicyPage = policiesDetailPage.addPolicy(AddHttpSecurityPluginPolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.HTTP_SECURITY_POLICY;
    }

    @Test
    public void shouldAddRequestPolicy() {
        addPolicyPage
            .hstsEnabled(true)
            .hstsIncludeSubdomains(true)
            .hstsMaxAge(30)
            .hstsPreload(true)
            .cspMode(CspMode.Enabled)
            .csp("test")
            .frameOptions(FrameOptions.Deny)
            .xssProtection(XssProtection.On)
            .contentTypeOptions(true)
            .addPolicy(AddHttpSecurityPluginPolicyPage.class);

        assertPolicyPresent();
    }

    @Test
    public void shouldNotAddRequestPolicy() {
        addPolicyPage
            .hstsEnabled(true)
            .xssProtection(XssProtection.Off)
            .cancel(AddHttpSecurityPluginPolicyPage.class);

        assertPolicyNotPresent();
    }
}
