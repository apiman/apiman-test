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
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;
import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgApisListPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@NoLocation
public class ImportApiPage<P> extends AbstractPage<P> {

    public SelenideElement backButton() {
        return $("#back");
    }

    public SelenideElement nextButton() {
        return $("#next");
    }

    public SelenideElement importButton() {
        return $("#import");
    }

    public P importApis() {
        importButton().click();
        return thisPageObject();
    }

    public SelenideElement finishButton() {
        return $("#finish");
    }

    public OrgApisListPage finish() {
        finishButton().click();
        return page(OrgApisListPage.class);
    }

    public SelenideElement cancelButton() {
        return $("#cancel");
    }

    public  <R> R cancel(Class<R> pageClass) {
        cancelButton().click();
        return page(pageClass);
    }
}
