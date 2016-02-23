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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.ByApiman;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * Component representing select element.
 * @author jkaspar
 */
public class Select2<P extends AbstractPage> {

    protected P thisPageObject;
    protected SelenideElement root;

    /**
     *
     * @param root root element
     * @param thisPageObject this page object
     */
    public Select2(SelenideElement root, P thisPageObject) {
        this.root = root;
        this.thisPageObject = thisPageObject;
    }

    /**
     *
     * @param rootSelector element selector for root element
     * @param thisPageObject this page object
     */
    public Select2(By rootSelector, P thisPageObject) {
        this($(rootSelector), thisPageObject);
    }

    /**
     *
     * @param modelProperty angular model property passed to {@link ByApiman#ngModel(String)}
     * @param thisPageObject this page object
     */
    public Select2(String modelProperty, P thisPageObject) {
        this($(ByApiman.ngModel(modelProperty)), thisPageObject);
    }

    /**
     * Dropdown button for selection
     * @return element
     */
    public SelenideElement button() {
        return root.find(".select2-choice");
    }

    public SelenideElement open() {
        button().click();
        dropDown().waitUntil(visible, Configuration.timeout);
        return dropDown();
    }

    /**
     * Dropdown menu of selection
     * @return element
     */
    public SelenideElement dropDown() {
        return root.find(".ui-select-dropdown");
    }

    /**
     * Options in the selection
     * @return element collection
     */
    public ElementsCollection options() {
        return dropDown().$(".ui-select-choices").$$("li[role='option']");
    }

    /**
     * Element holding selected value (first visible)
     * @return element
     */
    public SelenideElement selected() {
        return button().$$(".select2-chosen").find(visible);
    }

    /**
     * Picks the value in dropdown
     * @param selector selector used to find the correct option
     * @return this page object
     */
    public P select(Condition selector) {
        open();
        options().findBy(selector).click();
        return thisPageObject;
    }
    
    /**
     * Picks the value in selector using text
     * @param name text value
     * @return this page object
     */
    public P select(String name) {
        return select(text(name));
    }

    /**
     * Picks the value in selector using the exact text
     * @param name the exact text value
     * @return this page object
     */
    public P selectExact(String name) {
        return select(exactText(name));
    }
}
