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

package io.apiman.test.integration.ui.support.selenide.pages.apis.introduce;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.NoLocation;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * Choose Plans page - step 3
 * @author jkaspar
 */
@NoLocation
public class ImportApiStep3Page extends ImportApiPage<ImportApiStep3Page> {

    public ImportApiStep2Page back() {
        backButton().click();
        return page(ImportApiStep2Page.class);
    }

    public ImportApiStep4Page next() {
        nextButton().click();
        return page(ImportApiStep4Page.class);
    }

    /**
     * Checkbox that makes this apis public
     * @return checkbox
     */
    public SelenideElement publicApisCheckbox() {
        return $("#public-apis");
    }

    /**
     * Setter for public api checkbox
     * @param check true for check checkbox and false for uncheck
     * @return this page
     */
    public ImportApiStep3Page publicApis(boolean check) {
        if (publicApisCheckbox().isSelected() != check) {
            publicApisCheckbox().click();
        }
        return this;
    }

    /**
     * Find plan row by given planName
     * @param planName
     * @return element
     */
    public SelenideElement planContainer(String planName) {
        return $(By.xpath("//span[text()='" + planName + "']")).closest(".apiman-summaryrow");
    }

    /**
     * UI element - Checkbox for api plan tab
     * @return element
     */
    public SelenideElement planCheckbox(String planName) {
        return planContainer(planName).find("input[type='checkbox']");
    }

    /**
     * Version button for given planName
     * @param planName
     * @return element
     */
    public SelenideElement versionButton(String planName) {
        return planContainer(planName).find("button[data-toggle='dropdown']");
    }
}
