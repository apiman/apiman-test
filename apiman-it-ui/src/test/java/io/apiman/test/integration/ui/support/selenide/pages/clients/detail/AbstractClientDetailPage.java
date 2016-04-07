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

import io.apiman.test.integration.ui.support.selenide.ByApiman;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.BrowseApiPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateContractPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author ldimaggi, jcechace
 */
public abstract class AbstractClientDetailPage<P> extends AbstractDetailPage<P> {

    /**
     * New contract link
     * @return element
     */
    public SelenideElement newContractLink() {
        return $("a[data-field='ttd_toNewContract']");
    }

    /**
     * Click the new contract link
     * @return CreateContractPage
     */
    public CreateContractPage newContract() {
        return page(CreateContractPage.class);
    }

    /**
     * Button to register an clientApp
     * @return element
     */
    public SelenideElement registerButton() {
        return $(ByApiman.i18n("button", "register"));
    }

    /**
     * Register (publish) current clientApp version
     * @return this page
     */
    public P register() {
        registerButton().click();
        return thisPageObject();
    }

    /**
     * Button to Re-Register an client app
     * @return element
     */
    public SelenideElement reregisterButton() {
        return $(ByApiman.i18n("button", "reregister"));
    }

    /**
     * Re-Register current client app
     * @return this page
     */
    public P reregister() {
        reregisterButton().click();
        return thisPageObject();
    }

    /**
     * Button to unregister an clientApp
     * @return element
     */
    public SelenideElement unregisterButton() {
        return $(ByApiman.i18n("button", "unregister"));
    }

    /**
     * Unregister current clientApp version
     * @return this page
     */
    public P unregister() {
        unregisterButton().click();
        return thisPageObject();
    }

    /**
     * Link to search for a api for an clientApp
     * @return element
     */
    public SelenideElement searchForApisLink() {
        return $("a[data-field='ttd_toConsumeApis']");
    }

    /**
     * Search for apis available for contract
     * @return BrowseApiPage page object
     */
    public BrowseApiPage searchForApis() {
        searchForApisLink().click();
        return page(BrowseApiPage.class);
    }

    // Sidebar Tabs

    /**
     * Switch to policies management tab
     * @return ClientPoliciesDetailPage
     */
    public ClientPoliciesDetailPage policies() {
        return policies(ClientPoliciesDetailPage.class);
    }

    /**
     * Switch to activity management tab
     * @return ClientActivityDetailPage
     */
    public ClientActivityDetailPage activity() {
        return activity(ClientActivityDetailPage.class);
    }

    /**
     * Contracts menu tab
     * @return element
     */
    public SelenideElement contractsTab() {
        return $("#tab-contracts");
    }

    /**
     * Switch to contract management tab
     * @return ClientContractsDetailPage page object
     */
    public ClientContractsDetailPage contracts() {
        contractsTab().click();
        return page(ClientContractsDetailPage.class);
    }

    // Sidebar Tabs for registered apps

    /**
     * APIs menu tab
     * @return element
     */
    public SelenideElement apisTab() {
        return $("#tab-apis");
    }

    /**
     * Switch to APIs management tab
     * @return ClientApisDetailPage page object
     */
    public ClientApisDetailPage manageAPIs() {
        apisTab().click();
        return page(ClientApisDetailPage.class);
    }
}
