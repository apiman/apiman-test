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
public class AddJdbcBASICAuthenticationPolicyPage
        extends AddBASICAuthenticationPolicyPage<AddJdbcBASICAuthenticationPolicyPage> {

    @Override
    public AddJdbcBASICAuthenticationPolicyPage selectIdentitySource() {
        return identitySource("JDBC");
    }

    /**
     * JDBC datasource location input
     * @return element
     */
    public SelenideElement datasourceInput() {
        return $("#jdbc-datasource");
    }

    /**
     * Set value into {@link #datasourceInput()}
     * @param location value
     * @return this page object
     */
    public AddJdbcBASICAuthenticationPolicyPage datasource(String location) {
        datasourceInput().val(location);
        return this;
    }

    /**
     * JDBC SQL Query textarea
     * @return element
     */
    public SelenideElement queryTextarea() {
        return $("#jdbc-query");
    }

    /**
     * Set value into {@link #queryTextarea()}
     * @param query value
     * @return this page object
     */
    public AddJdbcBASICAuthenticationPolicyPage query(String query) {
        queryTextarea().val(query);
        return this;
    }

    /**
     * Div enclosing JDBC Password Hash Algorithm dropdown
     * @return element
     */
    public SelenideElement passHashAlgorithmRoot() {
        return passHashAlgorithmDropdownButton().parent();
    }

    /**
     * JDBC Password Hash Algorithm dropdown button
     * @return element
     */
    public SelenideElement passHashAlgorithmDropdownButton() {
        return $("button[data-id='jdbc-pwd-hash']");
    }

    /**
     * Dropdown listing JDBC Password Hash Algorithm options
     * @return element
     */
    public SelenideElement passHashAlgorithmDropdown() {
        return passHashAlgorithmRoot().find(".dropdown-menu");
    }

    /**
     * Options listed {@link #passHashAlgorithmDropdown()}
     * @param name visible text of option
     * @return element
     */
    public SelenideElement passHashAlgorithmOption(String name) {
        return passHashAlgorithmDropdown().findAll("a").find(Condition.text(name));
    }

    /**
     * Select identity source from {@link #passHashAlgorithmDropdown()}
     * @param source displayed identity source text label
     * @return element
     */
    public AddJdbcBASICAuthenticationPolicyPage passHashAlgorithm(String source) {
        passHashAlgorithmDropdownButton().click();
        passHashAlgorithmOption(source).click();
        return this;
    }

    /**
     * Also extract user roles from the DB checkbox
     * @return element
     */
    public SelenideElement extractRolesCheckbox() {
        return $("#jdbc-extract-roles");
    }

    /**
     * Select or deselect checkbox {@link #extractRolesCheckbox()} by given check value
     * @param check true for select and false for deselect the checkbox
     * @return this page object
     */
    public AddJdbcBASICAuthenticationPolicyPage extractRoles(boolean check) {
        if (check != extractRolesCheckbox().isSelected()) {
            extractRolesCheckbox().click();
        }
        return this;
    }

    /**
     * JDBC Roles SQL Query textarea
     * @return element
     */
    public SelenideElement roleQueryTextarea() {
        return $("#jdbc-role-query");
    }

    /**
     * Set value into {@link #roleQueryTextarea()}
     * @param query value
     * @return this page object
     */
    public AddJdbcBASICAuthenticationPolicyPage roleQuery(String query) {
        roleQueryTextarea().val(query);
        return this;
    }
}
