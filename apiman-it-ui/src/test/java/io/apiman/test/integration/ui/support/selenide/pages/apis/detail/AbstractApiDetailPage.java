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

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.layouts.AbstractDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiVersionPage;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateContractPage;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

/**
 * @author ldimaggi
 */
public abstract class AbstractApiDetailPage<P> extends AbstractDetailPage<P> {

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
     * Button to publish current api version
     * @return element
     */
    public SelenideElement publishButton() {
        return $(Selectors.withText("Publish"));
    }

    /**
     * Publish (lock) current api version
     * @return this page object
     */
    public P publish() {
        publishButton().click();
        return thisPageObject();
    }

    /**
     * Button to retire current api version
     * @return element
     */
    public SelenideElement retireButton() {
        return $("button[data-field='retireButton']");
    }

    /**
     * Retire current api version
     * @return this page object
     */
    public P retire() {
        retireButton().click();
        return thisPageObject();
    }

    @Override
    public SelenideElement newVersionLink() {
        return $("a[data-field='ttd_toNewApiVersion']");
    }

    /**
     * Click the new version link
     * @return CreateApiVersionPage
     */
    public CreateApiVersionPage newVersion() {
        return newVersion(CreateApiVersionPage.class);
    }

    // Sidebar Tabs

    /**
     * Switch to policies management tab
     * @return ApiDetailPolicPage
     */
    public ApiPoliciesDetailPage policies() {
        policiesTab().click();
        return page(ApiPoliciesDetailPage.class);
    }

    /**
     * Switch to activity management tab
     * @return ApiDetailActivPage
     */
    public ApiActivityDetailPage activity() {
        activityTab().click();
        return page(ApiActivityDetailPage.class);
    }

    /**
     * Element pointing to plans tab
     * @return element
     */
    public SelenideElement plansTab() {
        return $("#tab-plans");
    }

    /**
     * Switch to plans management tab
     * @return ApiDetailPlansPage page object
     */
    public ApiPlansDetailPage managePlans() {
        plansTab().click();
        return page(ApiPlansDetailPage.class);
    }

    /**
     * Element pointing to implementation tab
     * @return element
     */
    public SelenideElement implementationTab() {
        return $("#tab-impl");
    }

    /**
     * Switch to implementation management tab
     * @return ApiDetailImplPage page object
     */
    public ApiImplDetailPage manageImplementation() {
        implementationTab().click();
        return page(ApiImplDetailPage.class);
    }

    /**
     * Element pointing to definition tab
     * @return element
     */
    public SelenideElement definitionTab() {
        return $("#tab-def");
    }

    /**
     * Switch to definition management tab
     * @return ApiDefinitionDetailPage
     */
    public ApiDefinitionDetailPage manageDefinition() {
        definitionTab().click();
        return page(ApiDefinitionDetailPage.class);
    }

    // Sidebar Tabs for published api

    /**
     * Element pointing to contracts tab
     * @return element
     */
    public SelenideElement contractsTab() {
        return $("#tab-contracts");
    }

    /**
     * Switch to contracts management tab
     * @return ApiContractsDetailPage
     */
    public ApiContractsDetailPage manageContracts() {
        contractsTab().click();
        return page(ApiContractsDetailPage.class);
    }

    /**
     * Element pointing to endpoint tab
     * @return element
     */
    public SelenideElement endpointTab() {
        return $("#tab-endpoint");
    }

    /**
     * Switch to endpoint management tab
     * @return ApiEndpointDetailPage
     */
    public ApiEndpointDetailPage manageEndpoint() {
        endpointTab().click();
        return page(ApiEndpointDetailPage.class);
    }

    /**
     * Element pointing to metrics tab
     * @return element
     */
    public SelenideElement metricsTab() {
        return $("#tab-metrics");
    }

    /**
     * Switch to metrics management tab
     * @return ApiMetricsDetailPage
     */
    public ApiMetricsDetailPage manageMetrics() {
        metricsTab().click();
        return page(ApiMetricsDetailPage.class);
    }
}
