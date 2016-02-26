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

package io.apiman.test.integration.ui.tests.apis.detail;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.categories.VisualTest;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({VisualTest.class})
public class ApiOverviewDetailIT extends AbstractApiTest {

    @ApiVersion(api = "api", publish = false)
    private static ApiVersionBean apiVersion;

    private ApiDetailPage page;

    @Before
    public void openPage() throws Exception {
        page = open(
            ApiDetailPage.class,
            organization.getId(),
            api.getId(),
            apiVersion.getVersion()
        );
    }

    @Test
    public void shouldShowCorrectApiName() throws Exception {
        page.name()
            .shouldHave(text(api.getName()));
    }

    @Test
    public void shouldShowCorrectApiVersion() throws Exception {
        page.version()
            .shouldHave(text(apiVersion.getVersion()));
    }

    @Test
    public void shouldShowCorrectApiDescription() throws Exception {
        page.description()
            .shouldHave(text(api.getDescription()));
    }

    @Test
    public void shouldShowCorrectApiStatusOfUnpublishedVersion() throws Exception {
        page.status()
            .shouldHave(text("ready"));
    }
}
