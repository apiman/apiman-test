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

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
public interface UpdateFormButtons {

    /**
     * Update button
     * @return element
     */
    default SelenideElement updateButton() {
        return $("button[data-field='updateButton']");
    }

    /**
     * Click the update button
     * @param pageClass class representing following page
     * @return following page object
     */
    default  <F> F update(Class<F> pageClass) {
        updateButton().click();
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
