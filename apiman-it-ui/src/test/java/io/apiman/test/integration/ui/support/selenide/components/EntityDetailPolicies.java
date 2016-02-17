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

package io.apiman.test.integration.ui.support.selenide.components;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jkaspar
 */
public interface EntityDetailPolicies<P> extends RowEntries, PageComponent<P>, ModalDialog<P> {

    @Override
    default SelenideElement entriesContainer() {
        return $("apiman-policy-list .apiman-policies");
    }

    /**
     * Add policy button
     * @return element
     */
    default SelenideElement addPolicyButton() {
        return $($(".apiman-entity-content"), By.partialLinkText("Add Policy"));
    }

    /**
     * Add policy
     * @param pageClass class representing specific policy configuration page
     * @return page object representing policy configuration page
     */
    default <F extends AbstractAddPolicyPage> F addPolicy(Class<F> pageClass) {
        addPolicyButton().click();
        F page = page(pageClass);
        page.selectPolicyType();
        return page;
    }

    /**
     * Remove policy button
     * @param policyTitle identifying specific policy
     * @return element
     */
    default SelenideElement removeButton(String policyTitle) {
        return entryContainer(policyTitle).find(By.xpath("//button[text()='Remove']"));
    }

    /**
     * Remove policy
     * @param policyTitle identifying specific policy
     * @return this page object
     */
    default P removePolicy(String policyTitle) {
        removeButton(policyTitle).click();
        confirmDialog();
        return thisPageObject();
    }
}
