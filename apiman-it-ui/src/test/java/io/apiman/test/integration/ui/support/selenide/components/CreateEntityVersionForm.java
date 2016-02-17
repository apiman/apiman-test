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
public interface CreateEntityVersionForm<P> extends PageComponent<P>, CreateFormButtons {

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
     * Checkbox for clone of previous version
     * @return element
     */
    default SelenideElement cloneCheckbox() {
        return $("#cloneCB");
    }

    /**
     * Check clone checkbox
     * @param check true for check checkbox and false for uncheck
     * @return this page object
     */
    default P clone(boolean check) {
        if (cloneCheckbox().isSelected() != check) {
            cloneCheckbox().click();
        }
        return thisPageObject();
    }
}
