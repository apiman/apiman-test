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

package io.apiman.test.integration.ui.support.selenide.pages.plans.detail;

import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.layouts.AbstractDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.plans.CreatePlanVersionPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
public abstract class AbstractPlanDetailPage<P> extends AbstractDetailPage<P> {

    /**
     * Button to publish plan
     * @return element
     */
    public SelenideElement lockButton() {
        return $("button[data-field='lockButton']");
    }

    /**
     * Lock plan
     * @return this page object
     */
    public P lock() {
        lockButton().click();
        return thisPageObject();
    }

    /**
     * Click the new version link
     * @return CreatePlanVersionPage
     */
    public CreatePlanVersionPage newVersion() {
        return newVersion(CreatePlanVersionPage.class);
    }

    // Sidebar Tabs

    /**
     * Switch to policies management tab
     * @return PlanDetailPoliciesPage
     */
    public PlanPoliciesDetailPage policies() {
        return policies(PlanPoliciesDetailPage.class);
    }

    /**
     * Switch to activity management tab
     * @return PlanDetailActivityPage
     */
    public PlanActivityDetailPage activity() {
        return activity(PlanActivityDetailPage.class);
    }
}
