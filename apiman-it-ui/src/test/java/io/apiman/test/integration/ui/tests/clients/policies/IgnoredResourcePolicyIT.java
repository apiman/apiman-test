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

package io.apiman.test.integration.ui.tests.clients.policies;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.CollectionCondition.empty;

import io.apiman.test.integration.base.AbstractClientTest;
import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddIgnoredResourcePolicyPage;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class IgnoredResourcePolicyIT extends AbstractClientTest {

    private static final String IGNORED_PATH_FOO = "/foo";
    private static final String IGNORED_PATH_BAR = "/bar";

    private ClientVersions clientVersions;
    private AddIgnoredResourcePolicyPage addPolicyPage;

    @ClientVersion(client = "client", unique = true, publish = false)
    private ClientVersionBean version;

    @Before
    public void setup() {
        ClientPoliciesDetailPage policiesDetailPage = open(
            ClientPoliciesDetailPage.class,
            organization.getId(),
            client.getId(),
            version.getVersion()
        );

        clientVersions = new ClientVersions(client);
        clientVersions.fetch(version.getVersion());
        BeanAssert.assertNoPolicies(clientVersions);
        addPolicyPage = policiesDetailPage.addPolicy(AddIgnoredResourcePolicyPage.class);
    }

    private void assertPolicyPresent() {
        BeanAssert.assertPolicyPresent(clientVersions, PolicyDefs.IGNORED_RESOURCE);
    }

    private void addStartingConfiguration() {
        addPolicyPage
            .ignorePath(IGNORED_PATH_FOO)
            .ignorePath(IGNORED_PATH_BAR);
    }

    @Test
    public void canAddPolicyWithMultipleIgnoredPaths() {
        addStartingConfiguration();
        addPolicyPage
            .listedOptions().shouldHaveSize(2);
        addPolicyPage
            .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canRemovePathFromList() {
        addStartingConfiguration();
        addPolicyPage
            .removeOption(IGNORED_PATH_FOO)
            .listedOptions().shouldHaveSize(1);
        addPolicyPage.addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canClearPathList() {
        addStartingConfiguration();
        addPolicyPage
            .clearSelectList()
            .listedOptions().shouldBe(empty);
        addPolicyPage.addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canCancelConfiguration() {
        addStartingConfiguration();
        addPolicyPage.cancel(ApiPoliciesDetailPage.class);
        BeanAssert.assertNoPolicies(clientVersions);
    }
}
