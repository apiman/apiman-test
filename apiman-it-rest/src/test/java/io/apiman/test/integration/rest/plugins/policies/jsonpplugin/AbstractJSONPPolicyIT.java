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

package io.apiman.test.integration.rest.plugins.policies.jsonpplugin;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jkaspar
 */
@Plugin(artifactId = "apiman-plugins-jsonp-policy")
public abstract class AbstractJSONPPolicyIT extends AbstractApiTest {

    protected static final String CALLBACK_PARAM = "myCallback";
    protected static final String CALLBACK = "fooBar";
    protected static String echoApiPureResponse;

    protected abstract String getResourceURL();

    protected abstract String getApiEndpoint();

    @Before
    public void setUp() throws Exception {
        echoApiPureResponse = RestAssured.get(getApiEndpoint()).getBody().print();
    }

    @Test
    public void shouldNotChangeResponseBodyWhenCallbackIsNotSet() throws Exception {
        when()
            .get(getResourceURL())
        .then()
            .body(equalTo(echoApiPureResponse));
    }

    @Test
    public void shouldNotChangeResponseBodyLengthWhenCallbackIsNotSet() throws Exception {
        Integer expectedLength = echoApiPureResponse.length();

        when()
            .get(getResourceURL())
        .then()
            .header("Content-Length", equalTo(expectedLength.toString()));
    }

    @Test
    public void shouldWrapBodyInCallback() throws Exception {
        given()
            .queryParam(CALLBACK_PARAM, CALLBACK)
        .when()
            .get(getResourceURL())
        .then()
            .body(equalTo(CALLBACK + "(" + echoApiPureResponse + ")"));
    }

    @Test
    public void shouldIncreaseResponseBodyLengthByLengthOfAddedCallbackWrapper() throws Exception {
        Integer expectedLength = echoApiPureResponse.length() + CALLBACK.length() + 2;

        given()
            .queryParam(CALLBACK_PARAM, CALLBACK)
        .when()
            .get(getResourceURL())
        .then()
            .header("Content-Length", equalTo(expectedLength.toString()));
    }
}
