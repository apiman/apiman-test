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

package io.apiman.test.integration.ui.support.selenide.pages.apis.detail;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.partialLinkText;

import io.apiman.test.integration.ui.support.selenide.PageLocation;

import com.codeborne.selenide.SelenideElement;

/**
 * @author ldimaggi
 */
@PageLocation("/apimanui/api-manager/orgs/{0}/apis/{1}/{2}/plans")
public class ApiPlansDetailPage extends AbstractApiDetailPage<ApiPlansDetailPage> {

    /**
     * UI element - Button to save Api Implementation
     * @return element
     */
    public SelenideElement saveButton() {
        return $(withText("Save"));
    }

    /**
     * Save the configuration
     * @return this page
     */
    public ApiPlansDetailPage save() {
        saveButton().click();
        return this;
    }

	/**
	 * UI element - Checkbox for api plan tab
	 * @return element
	 */	
	public SelenideElement planCheckbox(String planName) {
        return planContainer(planName).find("input[type='checkbox']");
	}

	/**
	 * Add the plan to the api
	 * @param planName
	 * @return this page
	 */
	public ApiPlansDetailPage addPlan(String planName) {
		planCheckbox(planName).click();
		return this;
	}

    /**
     * Version button for given planName
     * @param planName
     * @return element
     */
    public SelenideElement versionButton(String planName) {
        return planContainer(planName).find("button[data-toggle='dropdown']");
    }

    /**
     * Checkbox that makes this api public
     * @return this page
     */
    public SelenideElement publicApiCheckbox() {
        return $("#public-api");
    }

    /**
     * Setter for public api checkbox
     * @param check true for check checkbox and false for uncheck
     * @return this page
     */
    public ApiPlansDetailPage publicApi(boolean check) {
        if (publicApiCheckbox().isSelected() != check) {
            publicApiCheckbox().click();
        }
        return this;
    }

    /**
     * Find plan row by given planName
     * @param planName
     * @return element
     */
    private SelenideElement planContainer(String planName) {
        return $(partialLinkText(planName)).closest(".apiman-summaryrow");
    }
}
