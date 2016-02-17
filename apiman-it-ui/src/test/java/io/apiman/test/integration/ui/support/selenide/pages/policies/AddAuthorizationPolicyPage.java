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

package io.apiman.test.integration.ui.support.selenide.pages.policies;

import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.NoLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jkaspar
 */
@NoLocation
public class AddAuthorizationPolicyPage extends AbstractAddPolicyPage<AddAuthorizationPolicyPage> {

    @Override
    public AddAuthorizationPolicyPage selectPolicyType() {
        return policyType("Authorization Policy");
    }

    /**
     * Path input inside Add Authorization Rule box
     * @return element
     */
    public SelenideElement newRulePathInput() {
        return $("#path");
    }

    /**
     * Set value into {@link #newRulePathInput()}
     * @param path value
     * @return this page object
     */
    public AddAuthorizationPolicyPage newRulePath(String path) {
        newRulePathInput().val(path);
        return this;
    }

    /**
     * Verb input inside Add Authorization Rule box
     * @return element
     */
    public SelenideElement newRuleVerbInput() {
        return $("#verb");
    }

    /**
     * Set value into {@link #newRuleVerbInput()}
     * @param verb value
     * @return this page object
     */
    public AddAuthorizationPolicyPage newRuleVerb(String verb) {
        newRuleVerbInput().val(verb);
        return this;
    }

    /**
     * Role input inside Add Authorization Rule box
     * @return element
     */
    public SelenideElement newRuleRoleInput() {
        return $("#role");
    }

    /**
     * Set value into {@link #newRuleRoleInput()}
     * @param role value
     * @return this page object
     */
    public AddAuthorizationPolicyPage newRuleRole(String role) {
        newRuleRoleInput().val(role);
        return this;
    }

    /**
     * Add rule button inside Add Authorization Rule box
     * @return element
     */
    public SelenideElement addRuleButton() {
        return $("#add-rule");
    }

    /**
     * Helpful method for adding new Authorization Rule
     * @param path of new rule
     * @param verb of new rule
     * @param role of new rule
     * @return this page object
     */
    public AddAuthorizationPolicyPage addAuthorizationRule(String path, String verb, String role) {
        newRulePath(path);
        newRuleVerb(verb);
        newRuleRole(role);
        addRuleButton().click();
        return this;
    }

    /**
     * Table element containing configured rules
     * TODO: element is missing identifier
     * @return element
     */
    public SelenideElement configuredRulesTable() {
        return $(By.xpath("//p[contains(text(), 'No authorization rules have been added')]")).closest("table");
    }

    /**
     * Elements inside configured rules table
     * @return elements collection
     */
    public ElementsCollection configuredRulesItems() {
        return configuredRulesTable().findAll("tbody > tr").filter(Condition.hasClass("ng-scope"));
    }

    /**
     * tr element containing configured rule with given text
     * @param text can be path, verb or required role
     * @return element
     */
    public SelenideElement configuredRuleItem(String text) {
        return configuredRulesTable().find(By.xpath("//td[contains(text(), '" + text + "')]")).closest("tr");
    }

    /**
     * Remove configured rule with given text
     * @param text can be path, verb or required role
     * @return element
     */
    public AddAuthorizationPolicyPage removeConfiguredRule(String text) {
        configuredRuleItem(text).find("button[ng-click='remove(item)']").click();
        return this;
    }
}
