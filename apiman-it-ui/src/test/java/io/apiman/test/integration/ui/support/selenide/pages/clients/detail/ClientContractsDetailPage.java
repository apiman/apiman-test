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

package io.apiman.test.integration.ui.support.selenide.pages.clients.detail;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.RowEntries;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateContractPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * Created by Jarek Kaspar
 */
@PageLocation("/apimanui/api-manager/orgs/{0}/clients/{1}/{2}/contracts")
public class ClientContractsDetailPage extends AbstractClientDetailPage<ClientContractsDetailPage> implements
    RowEntries {

    @Override public SelenideElement entriesContainer() {
        return $(".apiman-entity-content");
    }

    public SelenideElement breakAllContractsButton() {
        return $("button[data-field='breakAllContracts']");
    }

    public ClientContractsDetailPage breakAllContracts() {
        breakAllContractsButton().click();
        confirmDialog();
        return this;
    }

    public SelenideElement breakContractButton(String entityName) {
        entryContainer(entityName).hover();
        return entryContainer(entityName).find(By.xpath("//button[text()='Break Contract']"));
    }

    public ClientContractsDetailPage breakContract(String entityName) {
        breakContractButton(entityName).click();
        confirmDialog();
        return this;
    }

    public SelenideElement newContractButton() {
        return $("a[data-field=\"toNewContract\"]");
    }

    public CreateContractPage newContract() {
        newContractButton().click();
        return page(CreateContractPage.class);
    }
}
