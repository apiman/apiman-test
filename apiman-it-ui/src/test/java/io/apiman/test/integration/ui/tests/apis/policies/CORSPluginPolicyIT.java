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

import static com.codeborne.selenide.Condition.disabled;

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddCORSPolicyPage;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
@Plugin(artifactId = "apiman-plugins-cors-policy")
public class CORSPluginPolicyIT extends AbstractApiPolicyIT {

    private AddCORSPolicyPage addPolicyPage;

    @Before
    public void openPage() {
        addPolicyPage = policiesDetailPage.addPolicy(AddCORSPolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.CORS_POLICY;
    }

    @Test
    public void canAddPolicy() {
        addPolicyPage
            .terminateOnCORSError(false)
            .allowCredentials(false)
            .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canCancelConfiguration() {
        addPolicyPage
            .cancel(ApiPoliciesDetailPage.class);
        assertNoPolicies(apiVersions);
    }

    @Test
    public void canNotAddPolicyWithNegativeMaxAge() {
        addPolicyPage
            .maxAge("-1")
            .addPolicyButton().shouldBe(disabled);
    }

    @Test
    public void canAddPolicyWithMaxAge() {
        addPolicyPage
            .maxAge("1")
            .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    // Can add item to list

    @Test
    public void canAddItemIntoAllowOriginList() {
        canAddItemIntoList(addPolicyPage.allowOriginList(), "*");
    }

    @Test
    public void canAddItemIntoExposeHeadersList() {
        canAddItemIntoList(addPolicyPage.exposeHeadersList(), "foo");
    }

    @Test
    public void canAddItemIntoAllowHeadersList() {
        canAddItemIntoList(addPolicyPage.allowHeadersList(), "foo");
    }

    @Test
    public void canAddItemIntoAllowMethodsList() {
        canAddItemIntoList(addPolicyPage.allowMethodsList(), "GET");
    }

    private void canAddItemIntoList(AddCORSPolicyPage.CORSItemList list, String itemValue) {
        list.listedItems().shouldHaveSize(0);
        list.addItem(itemValue);
        list.listedItems().shouldHaveSize(1);

        addPolicyPage.addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    // Cannot add policy with duplicate items

    @Test
    public void cannotAddPolicyWithDuplicateAllowOriginItems() {
        cannotAddPolicyWithDuplicateItemsInList(addPolicyPage.allowOriginList(), "*");
    }

    @Test
    public void cannotAddPolicyWithDuplicateExposeHeadersItems() {
        cannotAddPolicyWithDuplicateItemsInList(addPolicyPage.exposeHeadersList(), "foo");
    }

    @Test
    public void cannotAddPolicyWithDuplicateAllowHeadersItems() {
        cannotAddPolicyWithDuplicateItemsInList(addPolicyPage.allowHeadersList(), "foo");
    }

    @Test
    public void cannotAddPolicyWithDuplicateAllowMethodsItems() {
        cannotAddPolicyWithDuplicateItemsInList(addPolicyPage.allowMethodsList(), "GET");
    }

    private void cannotAddPolicyWithDuplicateItemsInList(AddCORSPolicyPage.CORSItemList list, String itemValue) {
        list.addItem(itemValue);
        list.addItem(itemValue);
        addPolicyPage.addPolicyButton().shouldBe(disabled);
    }

    // Can delete all items from list

    @Test
    public void canDeleteAllItemsFromAllowOriginList() {
        canDeleteAllItemsFromList(addPolicyPage.allowOriginList());
    }

    @Test
    public void canDeleteAllItemsFromExposeHeadersList() {
        canDeleteAllItemsFromList(addPolicyPage.exposeHeadersList());
    }

    @Test
    public void canDeleteAllItemsFromAllowHeadersList() {
        canDeleteAllItemsFromList(addPolicyPage.allowHeadersList());
    }

    @Test
    public void canDeleteAllItemsFromAllowMethodsList() {
        canDeleteAllItemsFromList(addPolicyPage.allowMethodsList());
    }

    private void canDeleteAllItemsFromList(AddCORSPolicyPage.CORSItemList list) {
        list.addItem("foo");
        list.addItem("bar");
        list.deleteAllItems();
        list.listedItems().shouldHaveSize(0);

        addPolicyPage.addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    // Can delete last item from list

    @Test
    public void canDeleteLastItemFromAllowOriginItems() {
        canDeleteLastItemFromList(addPolicyPage.allowOriginList());
    }

    @Test
    public void canDeleteLastItemFromExposeHeadersList() {
        canDeleteLastItemFromList(addPolicyPage.exposeHeadersList());
    }

    @Test
    public void canDeleteLastItemFromAllowHeadersList() {
        canDeleteLastItemFromList(addPolicyPage.allowHeadersList());
    }

    @Test
    public void canDeleteLastItemFromAllowMethodsList() {
        canDeleteLastItemFromList(addPolicyPage.allowMethodsList());
    }

    private void canDeleteLastItemFromList(AddCORSPolicyPage.CORSItemList list) {
        list.addItem("foo");
        list.addItem("bar");
        list.listedItems().shouldHaveSize(2);
        list.deleteLastItem();
        list.listedItems().shouldHaveSize(1);
    }

}

