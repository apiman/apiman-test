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

import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
public abstract class AddLimitingPolicyPage<P extends AddLimitingPolicyPage> extends AbstractAddPolicyPage<P> {

    /**
     * UI element - number of api requests
     * @return element
     */
    public SelenideElement limitAmountInput() {
        return $("#num-requests");
    }

    /**
     * UI element - div enclosing granularity dropdown
     * TODO: element is missing identifier
     * @return element
     */
    public SelenideElement granularityRoot() {
        return granularityDropdownButton().parent();
    }

    /**
     * UI element - dropdown for rate limiting policy granularity
     * @return element
     */
    public SelenideElement granularityDropdownButton() {
        return $("button[data-id='granularity']");
    }

    /**
     * Dropdown listing granularity options
     * @return element
     */
    public SelenideElement granularityDropdown() {
        return granularityRoot().$(".dropdown-menu");
    }

    /**
     * Options listed in {@link #granularityDropdown()} ()}
     * @param text text of the option
     * @return element
     */
    public SelenideElement granularityDropDownOption(String text) {
        return granularityDropdown().$$("a").find(Condition.text(text));
    }

    /**
     * UI element - div enclosing period dropdown
     * TODO: element is missing identifier
     * @return element
     */
    public SelenideElement periodRoot() {
        return periodDropdownButton().parent();
    }

    /**
     * UI element - dropdown for rate limiting policy granularity
     * @return element
     */
    public SelenideElement periodDropdownButton() {
        return $("button[data-id='period']");
    }

    /**
     * Dropdown listing available period types
     * @return element
     */
    public SelenideElement periodDropdown() {
        return periodRoot().$(".dropdown-menu");
    }

    /**
     * Options listed in {@link #periodDropdown()} ()}
     * @param text text of the option
     * @return element
     */
    public SelenideElement periodDropdownOptions(String text) {
        return periodDropdown().$$("a").find(Condition.text(text));
    }

    /**
     * Select a granularity type from {@link #granularityDropdown()} ()}.
     *
     * @param granularity displayed granularity text label
     * @return this page object
     */
    public P granularity(String granularity) {
        granularityDropdownButton().click();
        granularityDropDownOption(granularity).click();
        return thisPageObject();
    }

    /**
     * Select a period type from {@link #periodDropdown()}
     *
     * @param period displayed period text label
     * @return this page object
     */
    public P period(String period) {
        periodDropdownButton().click();
        periodDropdownOptions(period).click();
        return thisPageObject();
    }

    /**
     * Set allowed number of requests
     *
     * @param limit amount
     * @return this page object
     */
    public P limitAmount(int limit) {
        limitAmountInput().val(String.valueOf(limit));
        return thisPageObject();
    }

    /**
     * Convince method allowing to perform the entire configuration
     * @param limit amount
     * @param granularity granularity
     * @param period period
     * @return this page object
     */
    public P configure(int limit, String granularity, String period) {
        limitAmount(limit);
        granularity(granularity);
        period(period);
        return thisPageObject();
    }

    // HEADERS

    /**
     * Limit Response Header input
     * @return element
     */
    public SelenideElement limitHeaderInput() {
        return $("#limit-header");
    }

    /**
     * Remaining Response Header input
     * @return element
     */
    public SelenideElement remainingHeaderInput() {
        return $("#remaining-header");
    }

    /**
     * Reset Response Header input
     * @return element
     */
    public SelenideElement resetHeaderInput() {
        return $("#reset-header");
    }

    /**
     * User ID Response Header input
     * @return element
     */
    public SelenideElement userIdHeaderInput() {
        return $("#user-header");
    }

    /**
     * Set value into limit header input
     * @param header name
     * @return this page object
     */
    public P limitHeader(String header) {
        limitHeaderInput().val(header);
        return thisPageObject();
    }

    /**
     * Set value into remaining header input
     * @param header name
     * @return this page object
     */
    public P remainingHeader(String header) {
        remainingHeaderInput().val(header);
        return thisPageObject();
    }

    /**
     * Set value into reset header input
     * @param header name
     * @return this page object
     */
    public P resetHeader(String header) {
        resetHeaderInput().val(header);
        return thisPageObject();
    }

    /**
     * Set value into user id header input
     * @param header name
     * @return this page object
     */
    public P userIdHeader(String header) {
        userIdHeaderInput().val(header);
        return thisPageObject();
    }
}
