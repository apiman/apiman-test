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

package io.apiman.test.integration.ui.support.selenide.pages.organizations;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.OrgEntitiesListPage;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@PageLocation("/api-manager/orgs/{0}/apis")
public class OrgApisListPage extends OrgEntitiesListPage<OrgApisListPage> {

    /**
     * New Api button
     * @return element
     */
    public SelenideElement newApiButton() {
        return $("a[apiman-i18n-key='new-api']");
    }

    /**
     * Click to New Api button
     * @return CreateApiPage
     */
    public CreateApiPage newApi() {
        newApiButton().click();
        return page(CreateApiPage.class);
    }
}
