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

package io.apiman.test.integration.rest.plugins.policies.simpleheader;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;
import static io.apiman.test.integration.runner.RestAssuredUtils.when;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by pstanko.
 */
@Plugin(artifactId = "apiman-plugins-simple-header-policy")
public abstract class AbstractSimpleHeaderPolicyResponseIT extends AbstractApiTest {

    protected static final String HEADER_NAME = "X-Response";
    protected static final String HEADER_VALUE = "This is response";
    protected static final String HEADER_CONNECTION = "Connection";

    protected abstract String getResourceURL();

    protected abstract String getApiEndpoint();

    private static String echoResponse;

    @Before
    public void setUp() throws Exception {
        echoResponse = givenGateway().get(getApiEndpoint())
            .getBody().print();
    }


    @Test
    public void requestStringHeaderShouldNotAddXREQ() throws Exception {

        final String req = new JsonPath(echoResponse)
            .get("headers." + HEADER_NAME);

        assertThat(req, isEmptyOrNullString());

    }

    @Test
    public void responseHeaderShouldHaveXREQ() throws Exception {
        when()
            .get(getResourceURL())
            .then()
            .header(HEADER_NAME, equalTo(HEADER_VALUE));

    }

    @Test
    public void responseHeaderShouldHaveNotConnection() throws Exception {
        when()
            .get(getResourceURL())
            .then()
            .header(HEADER_CONNECTION, isEmptyOrNullString());

    }
}
