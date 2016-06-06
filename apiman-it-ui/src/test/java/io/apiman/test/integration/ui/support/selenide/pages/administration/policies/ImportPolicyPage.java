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

import com.codeborne.selenide.SelenideElement;

/**
 *
 * @author jrumanov
 */
@PageLocation("/api-manager/import-policyDefs")
public class ImportPolicyPage {

    /**
     * Message about invalid JSON
     * @return Selenide element
     */
    public SelenideElement validationErrorMessage() {
        return $(".alert-warning");
    }

    /**
     * Text field for JSON
     * @return element
     */
    public SelenideElement jsonTextField(){
        return $("#data");
    }
    
    /**
     * Inserts policy definition in String into text field
     * @param jsonPolicyDef definition of policy in JSON
     * @return this page
     */
    public ImportPolicyPage jsonText(String jsonPolicyDef) {
        jsonTextField().setValue(jsonPolicyDef);
        return this;
    }

    /**
     * Import button
     * @return Selenide element
     */
    public SelenideElement importButton() {
        return $("#import");
    }

    /**
     * Performs import by clicking on Import button and then yes (I want JSON to
     * be imported) on the next page
     * @return page object representing PolicyDefsAdminPage
     */
    public PolicyDefsAdminPage importPolicyDefinition() {
        $("#import").click();
        $("#yes").click();
        return page(PolicyDefsAdminPage.class);
    }

    /**
     * Cancel button on a page
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
