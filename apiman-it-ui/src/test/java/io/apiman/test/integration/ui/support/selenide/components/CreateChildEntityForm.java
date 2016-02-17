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

package io.apiman.test.integration.ui.support.selenide.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
public interface CreateChildEntityForm<P> extends CreateEntityForm<P> {

    /**
     * Version input
     * @return element
     */
    default SelenideElement version() {
        return $("#apiman-version");
    }

    /**
     * Set the value of version input
     * @param version value
     * @return this page object
     */
    default P version(String version) {
        version().val(version);
        return thisPageObject();
    }

    /**
     * Button of organization selector
     * @return element
     */
    default SelenideElement organizationSelectorButton() {
        return $("#selector-org");
    }

    /**
     * Dropdown menu of organization selector
     * @return element
     */
    default SelenideElement organizationSelectorDropDown() {
        return $(".org .dropdown-menu");
    }

    /**
     * Options in organization selector dropdown menu
     * @return element collection
     */
    default ElementsCollection organizationSelectorOptions() {
        return organizationSelectorDropDown().findAll("a");
    }

    /**
     * Label holding selected organization name
     * @return element
     */
    default SelenideElement organization() {
        return $("span[data-field='selectorLabel']");
    }

    /**
     * Picks the value in organization selector
     * @param name value
     * @return this page object
     */
    default P organization(String name) {
        organizationSelectorButton().click();
        organizationSelectorOptions().findBy(exactText(name)).click();
        return thisPageObject();
    }
}
