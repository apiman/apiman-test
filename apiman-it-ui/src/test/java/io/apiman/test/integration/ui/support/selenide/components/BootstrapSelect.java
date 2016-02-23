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

import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * Class representing bootstrap select component.
 * Bootstrap select components are going to be replaced by Select2.
 * @author jkaspar
 */
@Deprecated
public class BootstrapSelect<P extends AbstractPage> extends Select2<P> {

    public BootstrapSelect(String modelProperty, P thisPageObject) {
        super(modelProperty, thisPageObject);
    }

    public BootstrapSelect(SelenideElement root, P thisPageObject) {
        super(root, thisPageObject);
    }

    public BootstrapSelect(By rootSelector, P thisPageObject) {
        super(rootSelector, thisPageObject);
    }

    @Override
    public SelenideElement button() {
        return root.find("button");
    }

    @Override
    public SelenideElement dropDown() {
        return root.find("ul.dropdown-menu");
    }

    @Override
    public ElementsCollection options() {
        return dropDown().$$("li > a");
    }

    @Override
    public SelenideElement selected() {
        return button().$("span.filter-option");
    }
}
