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

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;
import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.enabled;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiImplDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import org.junit.Test;

/**
 * @author opontes
 */
public class RepublishApiVersionIT extends AbstractApiTest {
    public static final String FOO_URL = "http://www.fooooo:123/foo";
    public static final int CHANGED_RETURN_CODE = 500;

    @ApiVersion(api = "api", unique = true)
    private ApiVersionBean apiVersionBean;

    @Test
    public void canNotRepublishWithoutChange() throws Exception {
        openApiImplDetailPage(apiVersionBean)
            .republishButton().shouldBe(disabled);
    }
    @Test
    public void canRepublishAfterChange() throws Exception {
        openApiImplDetailPage(apiVersionBean)
            .endpoint(FOO_URL)
            .save()
            .republishButton().shouldBe(enabled);
    }

    @Test
    public void canNotRepublishAgainWithoutChange() throws Exception {
        openApiImplDetailPage(apiVersionBean)
            .endpoint(FOO_URL)
            .save()
            .republish()
            .republishButton().shouldBe(disabled);
    }

    @Test
    public void canUpdateManagedEndpoint() throws Exception {
        openApiImplDetailPage(apiVersionBean)
            .endpoint(FOO_URL)
            .save()
            .republish();

        assertThat(getStatusCode(apiVersionBean), is(equalTo(CHANGED_RETURN_CODE)));
    }

    @Test
    public void canNotChangeGatewayBeanWithoutRepublishing() throws Exception {
        openApiImplDetailPage(apiVersionBean)
            .endpoint(FOO_URL)
            .save();

        assertThat(getStatusCode(apiVersionBean), is(not(equalTo(CHANGED_RETURN_CODE))));
    }

    private int getStatusCode(ApiVersionBean api){
        ApiVersions apiVersions = new ApiVersions(api.getApi());
        apiVersions.fetch(api.getVersion());
        return givenGateway().get(apiVersions.getManagedEndpoint()).getStatusCode();
    }

    private static ApiImplDetailPage openApiImplDetailPage(ApiVersionBean apiVersion) throws Exception {
        return open(ApiImplDetailPage.class,
            apiVersion.getApi().getOrganization().getId(),
            apiVersion.getApi().getId(),
            apiVersion.getVersion());
    }
}
