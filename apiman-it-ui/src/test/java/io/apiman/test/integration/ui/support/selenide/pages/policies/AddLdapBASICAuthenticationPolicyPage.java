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

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@NoLocation
public class AddLdapBASICAuthenticationPolicyPage
        extends AddBASICAuthenticationPolicyPage<AddLdapBASICAuthenticationPolicyPage> {

    @Override
    public AddLdapBASICAuthenticationPolicyPage selectIdentitySource() {
        return identitySource("LDAP");
    }

    /**
     * LDAP Server URL input
     * @return element
     */
    public SelenideElement serverUrlInput() {
        return $("#ldap-url");
    }

    /**
     * Set value into {@link #serverUrlInput()} input
     * @param value to be set
     * @return this page object
     */
    public AddLdapBASICAuthenticationPolicyPage serverUrl(String value) {
        serverUrlInput().val(value);
        return this;
    }

    /**
     * LDAP Bind DN input
     * @return element
     */
    public SelenideElement bindDNInput() {
        return $("#ldap-bind-dn");
    }

    /**
     * Set value into {@link #bindDNInput()} input
     * @param value to be set
     * @return this page object
     */
    public AddLdapBASICAuthenticationPolicyPage bindDN(String value) {
        bindDNInput().val(value);
        return this;
    }

    /**
     * Also extract user roles from the directory
     * @return element
     */
    public SelenideElement extractRolesCheckbox() {
        return $("#ldap-extract-roles");
    }

    /**
     * Select or deselect checkbox {@link #extractRolesCheckbox()}
     * @param check true for select, false for deselect
     * @return this page object
     */
    public AddLdapBASICAuthenticationPolicyPage extractRoles(boolean check) {
        if (check != extractRolesCheckbox().isSelected()) {
            extractRolesCheckbox().click();
        }
        return this;
    }

    /**
     * Group Membership Attribute input
     * @return element
     */
    public SelenideElement groupAttributeInput() {
        return $("#ldap-group-attr");
    }

    /**
     * Set value into {@link #groupAttributeInput()}
     * @param value to be set
     * @return this page object
     */
    public AddLdapBASICAuthenticationPolicyPage groupAttribute(String value) {
        groupAttributeInput().val(value);
        return this;
    }

    /**
     * Role Name Attribute input
     * @return this page object
     */
    public SelenideElement roleNameAttributeInput() {
        return $("#ldap-role-name-attr");
    }

    /**
     * Set value into {@link #roleNameAttributeInput()}
     * @param value to be set
     * @return this page object
     */
    public AddLdapBASICAuthenticationPolicyPage roleNameAttribute(String value) {
        roleNameAttributeInput().val(value);
        return this;
    }

    /**
     * Div enclosing 'Bind to LDAP As...' dropdown
     * @return element
     */
    public SelenideElement bindUsRoot() {
        return bindUsDropdownButton().parent();
    }

    /**
     * Bind to LDAP As dropdown button
     * @return element
     */
    public SelenideElement bindUsDropdownButton() {
        return $("button[data-id='ldap-bind-as']");
    }

    /**
     * Dropdown listing Bind to LDAP As options
     * @return element
     */
    public SelenideElement bindUsDropdown() {
        return bindUsRoot().find(".dropdown-menu");
    }

    /**
     * Options listed {@link #bindUsDropdown()}
     * @param name visible text of option
     * @return element
     */
    public SelenideElement bindUsOption(String name) {
        return bindUsDropdown().findAll("a").find(Condition.text(name));
    }

    /**
     * Select option from {@link #bindUsDropdown()}
     * @param source Bind to LDAP As source text label
     * @return element
     */
    public AddLdapBASICAuthenticationPolicyPage bindUs(String source) {
        bindUsDropdownButton().click();
        bindUsOption(source).click();
        return thisPageObject();
    }

    // Bind to LDAP As a service account

    /**
     * Service Account Username input
     * @return element
     */
    public SelenideElement serviceAccountUsernameInput() {
        return $("#ldap-sa-username");
    }

    /**
     * Set value into {@link #serviceAccountUsernameInput()}
     * @param value to be set
     * @return this page
     */
    public AddLdapBASICAuthenticationPolicyPage serviceAccountUsername(String value) {
        serviceAccountUsernameInput().val(value);
        return this;
    }

    /**
     * Service Account Password input
     * @return element
     */
    public SelenideElement serviceAccountPasswordInput() {
        return $("#ldap-sa-pass");
    }

    /**
     * Set value into {@link #serviceAccountPasswordInput()}
     * @param value to be set
     * @return this page
     */
    public AddLdapBASICAuthenticationPolicyPage serviceAccountPassword(String value) {
        serviceAccountPasswordInput().val(value);
        return this;
    }

    /**
     * Service Account password confirm input
     * @return element
     */
    public SelenideElement serviceAccountPasswordConfirmInput() {
        return $("#ldap-sa-pass-confirm");
    }

    /**
     * Set value into {@link #serviceAccountPasswordConfirmInput()}
     * @param value to be set
     * @return this page
     */
    public AddLdapBASICAuthenticationPolicyPage serviceAccountPasswordConfirm(String value) {
        serviceAccountPasswordConfirmInput().val(value);
        return this;
    }

    /**
     * User Search Base DN input
     * @return element
     */
    public SelenideElement userSearchBaseDNInput() {
        return $("#ldap-us-base-dn");
    }

    /**
     * Set value into {@link #userSearchBaseDNInput()}
     * @param value to be set
     * @return this page
     */
    public AddLdapBASICAuthenticationPolicyPage userSearchBaseDN(String value) {
        userSearchBaseDNInput().val(value);
        return this;
    }

    /**
     * User Search Expression input
     * @return element
     */
    public SelenideElement userSearchExpressionInput() {
        return $("#ldap-us-expr");
    }

    /**
     * Set value into {@link #userSearchExpressionInput()}
     * @param value to be set
     * @return this page
     */
    public AddLdapBASICAuthenticationPolicyPage userSearchExpression(String value) {
        userSearchExpressionInput().val(value);
        return this;
    }
}
