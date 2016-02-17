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

package io.apiman.test.integration.ui.tests.apis;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.test.integration.ui.support.selenide.pages.apis.CreateApiVersionPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author jrumanov
 */
@RunWith(ApimanRunner.class)
public class CloneApiVersionIT extends AbstractApiTest {

    private static final String CLONED_NAME = "2.0";

    private static ApiVersions apiVersions;

    @ApiVersion(api = "api", policies = @Policies("arbitrary"))
    private static ApiVersionBean version;

    @BeforeClass
    public static void setUp() {
        apiVersions = new ApiVersions(api);
    }

    public void versionCreatedByClone() {
    }

    @Test
    public void canCloneApiVersion() throws Exception {
        CreateApiVersionPage createVersionPage =
            open(CreateApiVersionPage.class, organization.getId(), api.getId(), version.getVersion());

        createVersionPage
            .version(CLONED_NAME)
            .clone(true)
            .create();

        apiVersions.peek(CLONED_NAME);
        apiVersions.fetch(CLONED_NAME);
        assertThat(apiVersions.policies().fetchAll().size(), is(1));
    }
}

