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

import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * Component representing list of items used on policy configuration pages.
 * @author jkaspar
 */
public class ItemList<P extends AbstractPage> {

    protected P thisPageObject;
    protected SelenideElement root;

    /**
     *
     * @param root root element
     * @param thisPageObject this page object
     */
    public ItemList(SelenideElement root, P thisPageObject) {
        this.root = root;
        this.thisPageObject = thisPageObject;
    }

    /**
     *
     * @param rootSelector element selector for root element
     * @param thisPageObject this page object
     */
    public ItemList(By rootSelector, P thisPageObject) {
        this($(rootSelector), thisPageObject);
    }

    public SelenideElement addItemButton() {
        return root.find("button[title*='Add']");
    }

    public P addItem() {
        addItemButton().click();
        return thisPageObject;
    }

    public SelenideElement deleteLastItemButton() {
        return root.find("button[title*='Delete Last']");
    }

    public P deleteLastItem() {
        deleteLastItemButton().click();
        return thisPageObject;
    }

    public SelenideElement deleteAllItemsButton() {
        return root.find("button[title='Delete All']");
    }

    public P deleteAllItems() {
        deleteAllItemsButton().click();
        return thisPageObject;
    }

    public ElementsCollection listedItems() {
        return root.findAll("div[data-schematype]").filter(Condition.visible);
    }
}
