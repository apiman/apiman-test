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
import static org.openqa.selenium.By.linkText;

import io.apiman.test.integration.ui.support.selenide.Layout;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@Layout("/browse/*")
public class BrowsePage extends AbstractPage<BrowsePage> {

    /**
     * Message block displayed when no results are present
     * @return element
     */
    public SelenideElement noContentInfo() {
        return $(".apiman-no-entities-description");
    }

    /**
     * Container for result elements
     * @return element
     */
    public SelenideElement resultContainer() {
        return $(".browse-items");
    }

    /**
     * Collection of results
     * @return elements collection
     */
    public ElementsCollection resultEntries() {
        return resultContainer().findAll(".item");
    }

    /**
     * Link to specific result
     * @return element
     */
    public SelenideElement resultLink(String title) {
        return resultContainer().find(linkText(title));
    }

    // Search

    /**
     * Search input
     * @return element
     */
    public SelenideElement searchInput() {
        return $("#apiman-search");
    }

    /**
     * Search button
     * @return element
     */
    public SelenideElement searchButton() {
        return $("#search-btn");
    }

    /**
     * Performs search operation
     * @param query value
     * @param clazz this page class object
     * @return this page object
     */
    public <ThisPageObject> ThisPageObject search(String query, Class<ThisPageObject> clazz) {
        searchInput().val(query);
        searchButton().click();
        return clazz.cast(this);
    }

    /**
     * Performs search operation
     * @param query value
     * @return this page object
     */
    public BrowsePage search(String query) {
        return search(query, BrowsePage.class);
    }
}
