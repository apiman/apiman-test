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

package io.apiman.test.integration.ui.support.selenide.pages.apis.introduce;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.NoLocation;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * Find Apis page - step 2
 * @author jkaspar
 */
@NoLocation
public class ImportApiStep2Page extends ImportApiPage<ImportApiStep2Page> {

    public ImportApiStep1Page back() {
        backButton().click();
        return page(ImportApiStep1Page.class);
    }

    public ImportApiStep3Page next() {
        nextButton().click();
        return page(ImportApiStep3Page.class);
    }

    public SelenideElement importFromRoot() {
        return $("#import-from").parent();
    }

    /**
     * Button used to open Import From selection dropdown
     * @return element
     */
    public SelenideElement importFromButton() {
        return importFromRoot().$("button[data-id='import-from']");
    }

    /**
     * Dropdown listing available Import From options
     * @return element
     */
    public SelenideElement importFromDropdown() {
        return importFromRoot().$(".dropdown-menu");
    }

    /**
     * Options listed in {@link #importFromDropdown()}
     * @param text text of the option
     * @return element
     */
    public SelenideElement policyDropDownOption(String text) {
        return importFromDropdown().$$("a").find(Condition.text(text));
    }

    /**
     * Select option from Import From select
     * @param source option
     * @return this page object
     */
    public ImportApiStep2Page importFrom(String source) {
        importFromButton().click();
        policyDropDownOption(source).click();
        return this;
    }

    public SelenideElement searchInput() {
        return $("input.input-search");
    }

    public SelenideElement searchButton() {
        return $("button[data-field=\"searchButton\"]");
    }

    public ImportApiStep2Page search(String query) {
        searchInput().val(query);
        searchButton().click();
        return this;
    }

    public SelenideElement searchResultsTable() {
        return $(".apiman-wizard-search-results table");
    }

    public ElementsCollection searchResults() {
        return searchResultsTable().findAll("tbody tr");
    }

    /**
     * Add api to the table of selected apis
     * @param text substring of api name or description
     * @return this page object
     */
    public ImportApiStep2Page selectApi(String text) {
        searchResults().findBy(Condition.text(text)).find("td:last-of-type a").click();
        return this;
    }

    public SelenideElement selectedApisTable() {
        return $(".apiman-wizard-page > div > div:last-of-type table");
    }

    public ElementsCollection selectedApis() {
        return selectedApisTable().findAll("tbody tr");
    }

    public ImportApiStep2Page removeApi(String text) {
        selectedApis().findBy(Condition.text(text)).find("td:last-of-type a").click();
        return this;
    }
}
