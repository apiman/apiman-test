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
import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.selenide.base.AbstractApiTest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import org.junit.Before;

/**
 * @author jcechace
 */
public abstract class AbstractApiPolicyIT extends AbstractApiTest {

    protected ApiVersions apiVersions;
    protected ApiPoliciesDetailPage policiesDetailPage;

    @ApiVersion(api = "api", unique = true, publish = false)
    private ApiVersionBean version;

    @Before
    public void setup() {
        policiesDetailPage = open(
            ApiPoliciesDetailPage.class,
            organization.getId(),
            api.getId(),
            version.getVersion()
        );

        apiVersions = new ApiVersions(api);
        apiVersions.fetch(version.getVersion());
        assertNoPolicies(apiVersions);
    }

    protected void assertPolicyPresent() {
        BeanAssert.assertPolicyPresent(apiVersions, getDefinitionId());
    }

    protected abstract String getDefinitionId();

}
