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
import io.apiman.test.integration.ui.support.selenide.components.BootstrapSelect;
import io.apiman.test.integration.ui.support.selenide.components.RulesTable;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@NoLocation
public class AddAuthorizationPolicyPage extends AbstractAddPolicyPage<AddAuthorizationPolicyPage>
    implements RulesTable<AddAuthorizationPolicyPage> {

    @Override
    public SelenideElement tableRoot() {
        return $("table");
    }

    @Override
    public AddAuthorizationPolicyPage selectPolicyType() {
        return policyType("Authorization Policy");
    }

    /**
     * Path input inside Add Authorization Rule box
     * @return element
     */
    public SelenideElement pathInput() {
        return $("#path");
    }

    /**
     * HTTP method select
     * @return BootstrapSelect
     */
    public BootstrapSelect<AddAuthorizationPolicyPage> newRuleMethodSelect() {
        return new BootstrapSelect<>($("#requestMethod").parent().$("div.bootstrap-select"), this);
    }

    /**
     * Role input inside Add Authorization Rule box
     * @return element
     */
    public SelenideElement roleInput() {
        return $("#role");
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
     * @param method of new rule
     * @param role of new rule
     * @return this page object
     */
    public AddAuthorizationPolicyPage addAuthorizationRule(String path, String method, String role) {
        pathInput().val(path);
        newRuleMethodSelect().select(method);
        roleInput().val(role);
        addRuleButton().click();
        return this;
    }

    public BootstrapSelect<AddAuthorizationPolicyPage> multipleMatchActionSelect() {
        return new BootstrapSelect<>($("#multiple-match-action").parent().$("div.bootstrap-select"), this);
    }

    public AddAuthorizationPolicyPage multipleMatchAction(String value) {
        multipleMatchActionSelect().select(value);
        return this;
    }

    public BootstrapSelect<AddAuthorizationPolicyPage> unmatchedRequestActionSelect() {
        return new BootstrapSelect<>($("#unmatched-request-action").parent().$("div.bootstrap-select"), this);
    }

    public AddAuthorizationPolicyPage unmatchedRequestAction(String value) {
        unmatchedRequestActionSelect().select(value);
        return this;
    }
}
