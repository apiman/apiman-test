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

package io.apiman.test.integration.ui.support.selenide.pages.administration.policies;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.UpdateFormButtons;

import com.codeborne.selenide.SelenideElement;

/**
 *
 * @author jrumanov
 */
@PageLocation("/apimanui/api-manager/admin/policyDefs/{0}")
public class EditPolicyPage implements UpdateFormButtons {

    /**
     * JSON text field element
     * @return Selenide element
     */
    public SelenideElement jsonTextField() {
        return $("#json");
    }

    /**
     * Inserts policy definition in String into text field
     * @param jsonDefinition definition of policy in JSON
     */
    public EditPolicyPage jsonTextField(String jsonDefinition) {
        jsonTextField().setValue(jsonDefinition);
        return this;
    }

    /**
     * Update button
     * @return Selenide element
     */
    public SelenideElement updateButton() {
        return $("#update-policy");
    }

    /**
     * Performs update of the policy by clicking on Update button and then yes
     * (I want JSON to be imported) on the next page
     * @return page object representing PolicyDefsAdminPage
     */
    public PolicyDefsAdminPage updatePolicy() {
        updateButton().click();
        //$("#yes").click(); //this is not yet present in the UI
        return page(PolicyDefsAdminPage.class);
    }

    /**
     * Invalid JSON message
     * @return Selenide element
     */
    public SelenideElement validationErrorMessage() {
        return $(".alert-warning");
    }

    /**
     * Cancel button
     * @return Selenide element
     */
    public SelenideElement cancelButton() {
        return $("#btn-cancel");
    }

    /**
     * Clicks the cancel button
     * @param pageClass class representing previous page
     * @return previous page object
     */
    public <R> R cancel(Class<R> pageClass) {
        cancelButton().click();
        return page(pageClass);
    }
}
