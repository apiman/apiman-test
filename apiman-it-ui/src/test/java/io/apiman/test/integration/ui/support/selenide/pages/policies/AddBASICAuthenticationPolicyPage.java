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
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@NoLocation
public abstract class AddBASICAuthenticationPolicyPage<P extends AddBASICAuthenticationPolicyPage>
        extends AbstractAddPolicyPage<P> {

    public abstract P selectIdentitySource();

    @Override
    public P selectPolicyType() {
        policyType("BASIC Authentication Policy");
        return selectIdentitySource();
    }

    /**
     * Authentication Realm input
     * @return element
     */
    public SelenideElement authenticationRealmInput() {
        return $("#realm");
    }

    /**
     * Set value into {@link #authenticationRealmInput()}
     * @param realm value
     * @return this page object
     */
    public P authenticationRealm(String realm) {
        authenticationRealmInput().val(realm);
        return thisPageObject();
    }

    /**
     * Require transport security checkbox
     * @return element
     */
    public SelenideElement requireTransportSecurityCheckbox() {
        return $("#requireTS");
    }

    /**
     * Select or deselect checkbox {@link #requireTransportSecurityCheckbox()} by given check value
     * @param check true for select and false for deselect the checkbox
     * @return this page object
     */
    public P requireTransportSecurity(boolean check) {
        if (check != requireTransportSecurityCheckbox().isSelected()) {
            requireTransportSecurityCheckbox().click();
        }
        return thisPageObject();
    }

    /**
     * Forward Authenticated Username as HTTP Header
     * @return element
     */
    public SelenideElement usernameHeaderInput() {
        return $("#forward-identity");
    }

    /**
     * Set value into {@link #usernameHeaderInput()}
     * @param header value
     * @return this page object
     */
    public P usernameHeader(String header) {
        usernameHeaderInput().val(header);
        return thisPageObject();
    }

    /**
     * Require Basic Authentication checkbox
     * @return element
     */
    public SelenideElement requireBasicAuthCheckbox() {
        return $("#requireBasic");
    }

    /**
     * Select or deselect checkbox {@link #requireBasicAuthCheckbox()} by given check value
     * @param check true for select and false for deselect the checkbox
     * @return this page object
     */
    public P requireBasicAuth(boolean check) {
        if (check != requireBasicAuthCheckbox().isSelected()) {
            requireBasicAuthCheckbox().click();
        }
        return thisPageObject();
    }

    /**
     * Div enclosing identity source dropdown
     * @return element
     */
    public SelenideElement identitySourceRoot() {
        return identitySourceDropdownButton().parent();
    }

    /**
     * Identity source dropdown button
     * @return element
     */
    public SelenideElement identitySourceDropdownButton() {
        return $("button[data-id='identity-source']");
    }

    /**
     * Dropdown listing identity source options
     * @return element
     */
    public SelenideElement identitySourceDropdown() {
        return identitySourceRoot().find(".dropdown-menu");
    }

    /**
     * Options listed {@link #identitySourceDropdown()}
     * @param name visible text of option
     * @return element
     */
    public SelenideElement identitySourceOption(String name) {
        return identitySourceDropdown().findAll("a").find(Condition.text(name));
    }

    /**
     * Select identity source from {@link #identitySourceDropdown()}
     * @param source displayed identity source text label
     * @return element
     */
    public P identitySource(String source) {
        identitySourceDropdownButton().click();
        identitySourceOption(source).click();
        return thisPageObject();
    }
}
