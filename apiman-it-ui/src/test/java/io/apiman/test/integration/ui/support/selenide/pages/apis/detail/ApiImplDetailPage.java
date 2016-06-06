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

import io.apiman.test.integration.ui.support.selenide.ByApiman;
import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.Select2;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

/**
 * @author ldimaggi
 */
@PageLocation("/api-manager/orgs/{0}/apis/{1}/{2}/impl")
public class ApiImplDetailPage extends AbstractApiDetailPage<ApiImplDetailPage> {

    /**
     * UI element - Text box for api endpoint
     * @return element
     */
    public SelenideElement endpoint() {
        return $("input[data-field='endpoint']");
    }

    /**
     * UI element - Pulldown for api API type
     * @return element
     */
    public SelenideElement apiType() {
        return apiTypeSelect().selected();
    }

    /**
     * Api API type dropdown
     * @return select2 component
     */
    public Select2<ApiImplDetailPage> apiTypeSelect() {
        return new Select2<>("updatedApi.endpointType", this);
    }

    /**
     * UI element - Button to save Api Implementation
     * @return element
     */
    public SelenideElement saveButton() {
        return Selenide.$(ByApiman.i18n("save"));
    }

    /**
     * Save the configuration
     * @return this page
     */
    public ApiImplDetailPage save() {
        saveButton().click();
        return this;
    }

    /**
     * Cancel button
     * @return element
     */
    public SelenideElement cancelButton() {
        return $("button[data-field='cancelButton']");
    }

    /**
     * Cancel the configuration
     * @return this page object
     */
    public ApiImplDetailPage cancel() {
        cancelButton().click();
        return this;
    }

    /**
     * Set api endpoint
     * @param val endpoint url
     * @return this page
     */
    public ApiImplDetailPage endpoint(String val) {
        endpoint().val(val);
        return this;
    }

    /**
     * Set endpoint API type
     * @param val endpoint API type
     * @return this page
     */
    public ApiImplDetailPage apiType(String val) {
        return apiTypeSelect().select(val);
    }
}
