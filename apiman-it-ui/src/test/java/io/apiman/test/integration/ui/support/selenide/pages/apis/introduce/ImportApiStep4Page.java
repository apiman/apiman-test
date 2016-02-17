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

package io.apiman.test.integration.ui.support.selenide.pages.apis.introduce;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.NoLocation;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * Confirm Import - step 4
 * @author jkaspar
 */
@NoLocation
public class ImportApiStep4Page extends ImportApiPage<ImportApiStep4Page> {

    public ImportApiStep3Page back() {
        backButton().click();
        return page(ImportApiStep3Page.class);
    }

    public SelenideElement apisTable() {
        return $(".apiman-import-wizard-apis table");
    }

    public ElementsCollection apis() {
        return apisTable().findAll("tbody tr");
    }
}
