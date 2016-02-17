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

import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jkaspar
 */
public interface RowEntries {

    /**
     * Container containing entries
     * @return element
     */
    SelenideElement entriesContainer();

    /**
     * Collection of entries
     * @return element collection
     */
    default ElementsCollection entries() {
        return entriesContainer().findAll(".apiman-summaryrow");
    }

    /**
     * Find link to entity detail page by entity name
     * @param entityName case insensitive, ignores multiple whitespaces between words
     * @return element
     */
    default SelenideElement entityLink(String entityName) {
        return entriesContainer().find(By.partialLinkText(entityName));
    }

    /**
     * Click on the entity link with given entity name
     * @param entityName value
     * @param clazz following page object class
     * @param <P> following page object
     * @return following page object
     */
    default <P> P openEntity(String entityName, Class<P> clazz) {
        entityLink(entityName).click();
        return page(clazz);
    }

    /**
     * Find entity description by entity name
     * @param entityName case insensitive, ignores multiple whitespaces between words
     * @return element
     */
    default SelenideElement entityDescription(String entityName) {
        return entryContainer(entityName).find(".description");
    }

    /**
     * Find entry container by given entity name.
     *
     * @param entityName case insensitive, ignores multiple whitespaces between words
     * @return element - entry container
     */
    default SelenideElement entryContainer(String entityName) {
        return entityLink(entityName).closest(".apiman-summaryrow");
    }

    /**
     * Find entries which meet given conditions
     * @param conditions used by filter
     * @return element collection
     */
    default ElementsCollection findEntries(Condition... conditions) {
        return entries().filterBy(Condition.and("Find entries with specific values", conditions));
    }
}
