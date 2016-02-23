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

import static com.codeborne.selenide.Condition.attribute;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * Table representing list of rules defined on policy pages
 * @author jkaspar
 */
public interface RulesTable<P extends RulesTable> extends PageComponent<P> {

    /**
     * Table element
     * @return SelenideElement
     */
    SelenideElement tableRoot();

    /**
     * Table rows
     * @return collection of tr elements
     */
    default ElementsCollection listedRules() {
        return tableRoot().findAll("tbody tr").filter(attribute("ng-repeat"));
    }

    /**
     * Find row in table by given conditions
     * @param conditions
     * @return tr element
     */
    default SelenideElement findRule(Condition... conditions) {
        return listedRules().findBy(Condition.and("Find items by specific values", conditions));
    }

    /**
     * Remove button
     * @param ruleText
     * @return
     */
    default SelenideElement removeRuleButton(String ruleText) {
        return findRule(Condition.hasText(ruleText)).find("td:last-of-type button");
    }

    /**
     * Remove row from table with specific text
     * @param ruleText
     * @return this page object
     */
    default P removeRule(String ruleText) {
        removeRuleButton(ruleText).click();
        return thisPageObject();
    }
}
