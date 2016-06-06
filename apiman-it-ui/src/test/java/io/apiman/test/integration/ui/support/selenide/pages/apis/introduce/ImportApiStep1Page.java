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

import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;

/**
 * Overview page - step 1
 * @author jkaspar
 */
@PageLocation("/api-manager/orgs/{0}/import/apis")
public class ImportApiStep1Page extends ImportApiPage<ImportApiStep1Page> {

    public ImportApiStep2Page next() {
        nextButton().click();
        return page(ImportApiStep2Page.class);
    }

}
