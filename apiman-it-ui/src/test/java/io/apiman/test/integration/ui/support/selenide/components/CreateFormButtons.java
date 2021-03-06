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
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
public interface CreateFormButtons {

    /**
     * Create button
     * @return element
     */
    default SelenideElement createButton() {
        return $("button[data-field='createButton']");
    }

    /**
     * Click the create button
     * @param pageClass class representing following page
     * @return following page object
     */
    default  <F> F create(Class<F> pageClass) {
        createButton().click();
        Selenide.sleep(Configuration.timeout); // TODO: possible propagation delay
        return page(pageClass);
    }

    /**
     * Cancel button
     * @return element
     */
    default SelenideElement cancelButton() {
        return $("button[data-field='cancelButton']");
    }

    /**
     * Click the cancel button
     * @param pageClass class representing previous page
     * @return previous page object
     */
    default  <R> R cancel(Class<R> pageClass) {
        cancelButton().click();
        return page(pageClass);
    }
}
