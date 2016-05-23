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

import static io.apiman.test.integration.ui.support.selenide.pages.policies.AddCircuitBreakerPluginPolicyPage.ErrorCodesItemList;

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddCircuitBreakerPluginPolicyPage;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by jsmolar.
 */
@Plugin(artifactId = "apiman-plugins-circuit-breaker-policy")
public class CircuitBreakerPluginPolicyIT extends AbstractApiPolicyIT {

    private AddCircuitBreakerPluginPolicyPage addPolicyPage;
    private ErrorCodesItemList list;

    @Before
    public void openPage() {
        addPolicyPage = policiesDetailPage.addPolicy(AddCircuitBreakerPluginPolicyPage.class);
        list = addPolicyPage.errorCodesList();
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.CIRCUIT_BREAKER_POLICY;
    }

    @Test
    public void shouldAddCircuitBreakerPolicy() {
        addPolicyPage
            .timeWindow(60)
            .limit(10)
            .reset(60)
            .failureCode(503)
            .addPolicy(AddCircuitBreakerPluginPolicyPage.class);

        assertPolicyPresent();
    }

    @Test
    public void shouldNotAddCircuitBreakerPolicy() {
        addPolicyPage
            .timeWindow(60)
            .cancel(AddCircuitBreakerPluginPolicyPage.class);

        assertPolicyNotPresent();
    }

    @Test
    public void shouldAddErrorCodes() {
        list.addErrorCodes(1);
        list.listedItems().shouldHaveSize(1);

        addPolicyPage.addPolicy(AddCircuitBreakerPluginPolicyPage.class);
        assertPolicyPresent();
    }

    @Test
    public void shouldDeleteLastItemFromErrorCodes() {
        list.addErrorCodes(3);
        list.deleteLastItem();
        list.listedItems().shouldHaveSize(2);
    }

    @Test
    public void shouldDeleteAllErrorCodes() {
        list.addErrorCodes(3);
        list.listedItems().shouldHaveSize(3);
        list.deleteAllItems();
        list.listedItems().shouldHaveSize(1);
    }

    @Test
    public void shouldDeleteOneErrorCode() {
        list.addErrorCodes(3);
        list.deleteItem(0);
        list.listedItems().shouldHaveSize(2);
    }

}
