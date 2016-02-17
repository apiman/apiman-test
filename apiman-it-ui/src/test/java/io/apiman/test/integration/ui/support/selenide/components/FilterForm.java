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

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
public interface FilterForm<P> extends PageComponent<P> {

    /**
     * Filter input
     * @return element
     */
    default SelenideElement filterInput() {
        return $(".apiman-filters .apiman-search-box input");
    }

    /**
     * Filter entries on current page
     * @param name partial name of the entity
     * @return this page object
     */
    default P filter(String name) {
        filterInput().val(name);
        return thisPageObject();
    }

    /**
     * Clear button
     * @return element
     */
    default SelenideElement clearButton() {
        return $(".apiman-filters .apiman-search-box button");
    }

    /**
     * Clear filter
     * @return this page object
     */
    default P clearFilter() {
        clearButton().click();
        return thisPageObject();
    }

    /**
     * Message block displayed when no results are present
     * @return element
     */
    default SelenideElement noContentInfo() {
        return $(".apiman-no-entities-description");
    }
}
