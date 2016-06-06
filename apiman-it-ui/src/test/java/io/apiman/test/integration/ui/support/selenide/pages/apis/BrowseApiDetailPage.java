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

package io.apiman.test.integration.ui.support.selenide.pages.apis;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.RowEntries;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateContractPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jcechace
 */
@PageLocation("/api-manager/browse/orgs/{0}/{1}/{2}")
public class BrowseApiDetailPage extends AbstractPage implements RowEntries {

    /**
     * Container holding information (name, description, version) about this api
     * @return element
     */
    public SelenideElement apiContainer() {
        return $(".browse-items .item");
    }

    /**
     * Name of the api displayed on this page
     * @return element
     */
    public SelenideElement apiTitle() {
        return apiContainer().find(".title span:last-of-type");
    }

    /**
     * Description of the api displayed on this page
     * @return element
     */
    public SelenideElement apiDescription() {
        return apiContainer().find(".description");
    }

    /**
     * Link to the organization, which maintaining api displayed on this page
     * @return element
     */
    public SelenideElement organizationLink() {
        return apiContainer().find(".title a[data-field='titleOrg']");
    }

    /**
     * Textarea maintaining endpoint of public api
     * @return element
     */
    public SelenideElement endpointTextarea() {
        return $("#managed-endpoint-wrap input");
    }

    // Entries

    @Override
    public SelenideElement entriesContainer() {
        return $("#available-plans-wrap");
    }

    /**
     * Create contract button for given plan
     * @param planName value
     * @return element
     */
    public SelenideElement createContractButton(String planName) {
        return entryContainer(planName).find(By.partialLinkText("Create Contract"));
    }

    /**
     * Create contract under specified plan
     * @param planName value
     * @return element
     */
    public CreateContractPage createContract(String planName) {
        createContractButton(planName).click();
        return page(CreateContractPage.class);
    }

    // Api version selector

    /**
     * Container for api version selector
     * @return element
     */
    public SelenideElement versionSelectorContainer() {
        return $("#version").parent();
    }

    /**
     * Button for api version selector
     * @return element
     */
    public SelenideElement versionSelectorButton() {
        return versionSelectorContainer().find("button");
    }

    /**
     * Dropdown menu of api version selector
     * @return element
     */
    public SelenideElement versionSelectorDropdown() {
        return versionSelectorContainer().find("ul.dropdown-menu");
    }

    /**
     * Options in version selector dropdown menu
     * @return collection of elements
     */
    public ElementsCollection versionSelectorOptions() {
        return versionSelectorDropdown().findAll(".text");
    }

    /**
     * Element holding selected version
     * @return element
     */
    public SelenideElement apiVersion() {
        return versionSelectorButton().find("span:first-of-type");
    }

    /**
     * Pick the value in api version selector
     * @param version value
     * @return this page object
     */
    public BrowseApiDetailPage apiVersion(String version) {
        versionSelectorButton().click();
        versionSelectorOptions().findBy(exactText(version)).closest("a").click();
        return this;
    }
}
