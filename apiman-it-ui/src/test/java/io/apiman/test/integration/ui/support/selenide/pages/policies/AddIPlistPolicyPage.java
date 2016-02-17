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

import io.apiman.test.integration.ui.support.selenide.components.SelectList;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jcechace
 */
public abstract class AddIPlistPolicyPage<P extends AddIPlistPolicyPage> extends AbstractAddPolicyPage<P>
        implements SelectList<P> {

    @Override
    public SelenideElement elementSelect() {
        return $("#ip-addresses");
    }

    /**
     * Input used to specify http header
     * @return element
     */
    public SelenideElement headerInput() {
        return $("#http-header");
    }

    /**
     * Div enclosing failure code dropdown
     * TODO: element is missing identifier
     * @return element
     */
    public SelenideElement failureCodeRoot() {
        return failureCodeDropdownButton().parent();
    }

    /**
     * Dropdown for failure code
     * @return element
     */
    public SelenideElement failureCodeDropdownButton() {
        return $("button[data-id='failure-code']");
    }

    /**
     * Dropdown listing failure code options
     * @return element
     */
    public SelenideElement failureCodeDropdown() {
        return failureCodeRoot().$(".dropdown-menu");
    }

    /**
     * Options listed in {@link #failureCodeDropdown()} ()}
     * @param text text of the option
     * @return element
     */
    public SelenideElement failureCodeDropDownOption(String text) {
        return failureCodeDropdown().$$("a").find(Condition.text(text));
    }

    /**
     * input used to enter the ip address
     * @return element
     */
    public SelenideElement addressInput() {
        return $("#ip-address");
    }

    /**
     * Add ip to {@link #elementSelect()}
     * @param path path to be added
     * @return this page object
     */
    public AddIPlistPolicyPage addAddress(String path) {
        addressInput().val(path);
        addOptionButton().click();
        return thisPageObject();
    }

    /**
     * Specify http header
     * @param header value
     * @return this page object
     */
    public AddIPlistPolicyPage header(String header) {
        headerInput().val(header);
        return thisPageObject();
    }

    /**
     * Select response failure code from {@link #failureCodeDropdown()}
     * @param code part of display code text (e.g. "404")
     * @return this page object
     */
    public AddIPlistPolicyPage failureCode(String code) {
        failureCodeDropdownButton().click();
        failureCodeDropDownOption(code).click();
        return thisPageObject();
    }
}
