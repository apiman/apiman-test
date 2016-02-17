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

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

/**
 * Component representing list of 'option' elements inside 'select' element.
 * This component contain Clear, Remove, and Add button, but not input elements.
 * @author jkaspar
 */
public interface SelectList<P extends SelectList> extends PageComponent<P> {

    /**
     * Select element
     * @return element
     */
    SelenideElement elementSelect();

    /**
     * Root element for select element and clear, remove buttons
     * @return element
     */
    default SelenideElement selectRoot() {
        return elementSelect().parent();
    }

    /**
     * Button used to remove all options from {@link #elementSelect()}
     * @return element
     */
    default SelenideElement clearButton() {
        return selectRoot().find(By.xpath("//button[text()='Clear']"));
    }

    /**
     * Button used to remove selected options from {@link #elementSelect()}
     * @return element
     */
    default SelenideElement removeButton() {
        return selectRoot().find(By.xpath("//button[text()='Remove']"));
    }

    /**
     * Button used for adding new option into select element
     * @return element
     */
    default SelenideElement addOptionButton() {
        return selectRoot().parent().find(By.xpath("//button[text()='Add']"));
    }

    /**
     * Option elements of {@link #elementSelect()}
     * @return elements collection
     */
    default ElementsCollection listedOptions() {
        return elementSelect().findAll("option");
    }

    /**
     * Remove option from {@link #elementSelect()}
     * @param name of the identity that will be removed
     * @return this page object
     */
    default P removeOption(String name) {
        Select select = new Select(elementSelect());
        select.deselectAll();
        select.selectByVisibleText(name);

        removeButton().click();
        return thisPageObject();
    }

    /**
     * Clear {@link #elementSelect()}
     * @return this page object
     */
    default P clearSelectList() {
        clearButton().click();
        return thisPageObject();
    }
}
