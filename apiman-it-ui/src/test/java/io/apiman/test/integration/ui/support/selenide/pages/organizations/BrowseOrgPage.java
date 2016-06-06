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

import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.BrowsePage;

/**
 * @author jcechace
 */
@PageLocation("/api-manager/browse/orgs")
public class BrowseOrgPage extends BrowsePage {

    @Override
    public BrowseOrgPage search(String orgName) {
        return search(orgName, BrowseOrgPage.class);
    }

    /**
     * Open (click) page result with specified link text
     * @param linkText title of result link
     * @return page object representing BrowseOrgDetailPage
     */
    public BrowseOrgDetailPage openResult(String linkText) {
        resultLink(linkText).click();
        return page(BrowseOrgDetailPage.class);
    }
}
