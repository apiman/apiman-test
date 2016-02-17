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

package io.apiman.test.integration.ui.support.selenide.layouts;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.components.Select2;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

/**
 * Created by Jarek Kaspar
 */
@Layout("/orgs/{id}/*/{id}/{version}/new-policy")
public abstract class AbstractAddPolicyPage<P extends AbstractAddPolicyPage> extends AbstractPage<P> {

    /**
     * Add policy button
     * @return element
     */
    public SelenideElement addPolicyButton() {
        return $("#add-policy");
    }

    /**
     * Cancel button
     * @return element
     */
    public SelenideElement cancelButton() {
        return $("#cancel");
    }

    /**
     * Click the cancel button
     * @param pageClass class representing previous page
     * @return previous page object
     */
    public  <R> R cancel(Class<R> pageClass) {
        cancelButton().click();
        return page(pageClass);
    }

    /**
     * Add policy by clicking on the "Add Policy" button
     * @param pageClass class representing following page
     * @return following page object
     */
    public <F> F addPolicy(Class<F> pageClass) {
        addPolicyButton().shouldBe(Condition.enabled);
        addPolicyButton().click();
        Selenide.sleep(Configuration.timeout); // TODO: possible propagation delay
        return page(pageClass);
    }

    /**
     * Select a policy type from dropdown select.
     *
     * @param policy displayed policy name (text)
     * @return page object representing configuration of selected policy
     */
    public P policyType(String policy) {
        Select2<P> select = new Select2<>("selectedDefId", thisPageObject());
        return select.selectExact(policy);
    }

    public abstract P selectPolicyType();
 }
