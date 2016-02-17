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

package io.apiman.test.integration.ui.support.selenide.pages.clients;

import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.ByApiman;
import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.CreateFormButtons;
import io.apiman.test.integration.ui.support.selenide.components.Select2;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientContractsDetailPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

/**
 * @author ldimaggi
 */
@PageLocation("/apimanui/api-manager/new-contract")
public class CreateContractPage extends AbstractPage<CreateContractPage>
    implements CreateFormButtons {

    public Select2<CreateContractPage> clientSelect() {
        return new Select2<>("selectedClient", this);
    }

    public Select2<CreateContractPage> clientVersionSelect() {
        return new Select2<>("selectedClientVersion", this);
    }

    public Select2<CreateContractPage> planSelect() {
        return new Select2<>("selectedPlan", this);
    }

    public Select2<CreateContractPage> apiVersionSelect() {
        return new Select2<>("selectedApiVersion", this);
    }

    /**
     * Button used for selecting api
     * @return element
     */
    public SelenideElement selectApiButton() {
        return $("#api");
    }

    /**
     * After user select api, this button displays selected api and give possibility to change selected api
     * @return element
     */
    public SelenideElement selectedApiButton() {
        return Selenide.$(ByApiman.ngShow("selectedApi"));
    }

    /**
     * Create contract button
     * @return element
     */
    @Override
    public SelenideElement createButton() {
        return $("#create-contract");
    }

    /**
     * Create the contract for the client's managed api
     */
    public ClientContractsDetailPage create() {
        return create(ClientContractsDetailPage.class);
    }

    public CreateContractPage selectApi(String name) {
        selectApiButton().click();
        searchApi(name);
        selectApiFromSearchResults(name);
        return thisPageObject();
    }

    public CreateContractPage selectApiVersion(String name, String version) {
        selectApi(name);
        apiVersionSelect().select(version);
        selectApiOkButton().click();
        $(".modal-dialog").waitWhile(present, Configuration.timeout);
        return thisPageObject();
    }

    public CreateContractPage selectClientVersion(String name, String version) {
        clientSelect().select(name);
        clientVersionSelect().select(version);
        return thisPageObject();
    }

    public CreateContractPage selectPlan(String name) {
        planSelect().select(name);
        return thisPageObject();
    }

    // Select Api Window

    public SelenideElement selectApiWindow() {
        return $(".modal-content");
    }

    public SelenideElement searchApiForm() {
        return  selectApiWindow().find(".modal-select-api > form");
    }

    public SelenideElement searchApiInput() {
        return searchApiForm().find("input");
    }

    public SelenideElement searchApiButton() {
        return searchApiForm().find("button");
    }

    public CreateContractPage searchApi(String apiName) {
        searchApiInput().val(apiName);
        searchApiButton().click();
        return thisPageObject();
    }

    public ElementsCollection searchApiResults() {
        return selectApiWindow().find(".input-search-results").findAll("a");
    }

    public CreateContractPage selectApiFromSearchResults(String apiName) {
        searchApiResults().findBy(Condition.text(apiName)).click();
        return thisPageObject();
    }

    public SelenideElement selectApiOkButton() {
        return selectApiWindow().find(".modal-footer button:last-of-type");
    }

    public SelenideElement selectApiCancelButton() {
        return selectApiWindow().find(".modal-footer button:first-of-type");
    }
}
