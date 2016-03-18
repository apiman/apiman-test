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

import static io.apiman.test.integration.ui.support.assertion.BeanAssert.assertApiVersion;
import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static org.hamcrest.core.IsEqual.equalTo;

import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.ui.support.selenide.base.AbstractApiUITest;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiImplDetailPage;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.apis.EndpointType;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
public class ApiImplDetailIT extends AbstractApiUITest {

    private static final String NEW_ENDPOINT = "http://localhost:8080/foo/bar";
    private static final String NEW_API_TYPE = EndpointType.soap.toString();

    private ApiImplDetailPage page;

    @ApiVersion(api = "api", publish = false, unique = true)
    private ApiVersionBean apiVersion;

    @Before
    public void openPage() throws Exception {
        page = open(ApiImplDetailPage.class,
            organization.getId(),
            api.getId(),
            apiVersion.getVersion());
    }

    @Test
    public void shouldHaveCorrectApiEndpoint() throws Exception {
        page.endpoint()
            .shouldHave(value(apiVersion.getEndpoint()));
    }

    @Test
    public void shouldHaveCorrectApiType() throws Exception {
        page.apiType()
            .shouldHave(text(apiVersion.getEndpointType().toString()));
    }

    @Test
    public void canChangeApiEndpoint() throws Exception {
        page.endpoint(NEW_ENDPOINT).save();

        page.endpoint()
            .shouldHave(value(NEW_ENDPOINT));

        assertApiVersion(apiVersion, new ResponseSpecBuilder()
            .expectBody("endpoint", equalTo(NEW_ENDPOINT))
            .build());
    }

    @Test
    public void canChangeApiType() throws Exception {
        page.apiType(NEW_API_TYPE).save();

        page.apiType()
            .shouldHave(text(NEW_API_TYPE));

        assertApiVersion(apiVersion, new ResponseSpecBuilder()
            .expectBody("endpointType", equalTo(NEW_API_TYPE))
            .build());
    }

    @Test
    public void shouldNotChangeAnythingAfterCancel() throws Exception {
        page.endpoint(NEW_ENDPOINT)
            .apiType(NEW_API_TYPE)
            .cancel();

        page.endpoint()
            .shouldHave(value(apiVersion.getEndpoint()));
        page.apiType()
            .shouldHave(text(apiVersion.getEndpointType().toString()));

        assertApiVersion(apiVersion, new ResponseSpecBuilder()
            .expectBody("endpoint", equalTo(apiVersion.getEndpoint()))
            .expectBody("endpointType", equalTo(apiVersion.getEndpointType().toString()))
            .build());
    }
}
