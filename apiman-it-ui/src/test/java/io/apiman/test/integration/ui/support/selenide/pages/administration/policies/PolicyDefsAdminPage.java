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
import io.apiman.test.integration.ui.support.selenide.components.FilterForm;
import io.apiman.test.integration.ui.support.selenide.components.RowEntries;
import io.apiman.test.integration.ui.support.selenide.layouts.AdminPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 *
 * @author jrumanov
 */
@PageLocation("/apimanui/api-manager/admin/policyDefs")
public class PolicyDefsAdminPage extends AdminPage<PolicyDefsAdminPage> implements
    FilterForm<PolicyDefsAdminPage>,
    RowEntries {

    @Override
    public SelenideElement entriesContainer() {
        return $(".admin-content");
    }

    /**
     * Open page result with specified link text
     * @param linkText title of result link
     * @return page object representing EditPolicyPage
     */
    public EditPolicyPage openEntity(String linkText) {
        return openEntity(linkText, EditPolicyPage.class);
    }

    /**
     * import button
     * @return element
     */
    public SelenideElement importPolicyButton() {
        return $(By.linkText("Import Policy"));
    }

    /**
     * Performs linking to import policy page
     * @return page object representing ImportPolicyPage
     */
    public ImportPolicyPage importPolicy() {
        importPolicyButton().click();
        return page(ImportPolicyPage.class);
    }
}
