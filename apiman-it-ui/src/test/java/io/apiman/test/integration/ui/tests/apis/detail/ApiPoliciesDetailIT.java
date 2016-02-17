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

package io.apiman.test.integration.ui.tests.apis.detail;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Condition.visible;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class ApiPoliciesDetailIT extends AbstractApiTest {

    private static final String POLICY_TITLE = "Ignored Resources";

    @ApiVersion(api = "api",
        publish = false,
        version = "unpublished", unique = true,
        policies = @Policies("arbitrary"))
    private ApiVersionBean unpublishedVersion;

    @ApiVersion(api = "api",
        version = "published",
        unique = true,
        policies = @Policies("arbitrary"))
    private ApiVersionBean publishedVersion;

    public ApiPoliciesDetailPage openPage(ApiVersionBean apiVersion) {
        return open(ApiPoliciesDetailPage.class,
            organization.getId(),
            api.getId(),
            apiVersion.getVersion());
    }

    @Test
    public void shouldShowAddPolicyButtonOnUnpublishedVersion() throws Exception {
        openPage(unpublishedVersion)
            .addPolicyButton().shouldBe(visible);
    }

    @Test
    public void canRemovePolicy() throws Exception {
        openPage(unpublishedVersion)
            .removePolicy(POLICY_TITLE)
            .entries()
            .shouldHaveSize(0);

        ApiVersions apiVersions = new ApiVersions(api);
        apiVersions.fetch(unpublishedVersion.getVersion());
        int policiesCount = apiVersions.policies().fetchAll().size();
        Assert.assertEquals("Api version should't have any policy", 0, policiesCount);
    }

    @Test
    public void shouldDisplayCorrectNumberOfPolicies() throws Exception {
        openPage(publishedVersion)
            .entries().shouldHaveSize(1);
    }

    @Test
    public void shouldDisplaySpecificPolicy() throws Exception {
        openPage(publishedVersion)
            .entityLink(POLICY_TITLE).shouldBe(present);
    }
}
