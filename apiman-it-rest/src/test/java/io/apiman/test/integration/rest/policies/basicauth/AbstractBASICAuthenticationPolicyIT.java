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

package io.apiman.test.integration.rest.policies.basicauth;

import static com.jayway.restassured.RestAssured.given;

import io.apiman.test.integration.base.AbstractApiTest;

import org.junit.Test;

/**
 * @author jcechace
 */
public abstract class AbstractBASICAuthenticationPolicyIT extends AbstractApiTest {

    protected void doAuthRequest(String username, String password, int statusCode) {
        given().
            auth().basic(username, password).
        when().
            get(getResourceURL()).
        then().
            statusCode(statusCode);
    }

    protected abstract String getResourceURL();

    @Test
    public void shouldPassWhenValidCredentialsProvided() {
        doAuthRequest(PolicyConstants.USERNAME, PolicyConstants.PASSWORD, 200);
    }

    @Test
    public void shouldFailWhenInvalidUsernameProvided() {
        doAuthRequest("invalid", PolicyConstants.PASSWORD, 401);
    }

    @Test
    public void shouldFailWhenInvalidPasswordProvided() {
        doAuthRequest(PolicyConstants.USERNAME, "invalid", 401);
    }

    @Test
    public void shouldFailWhenNoCredentialsProvided() {
        getRequest(getResourceURL(), 401);
    }

    @Override
    public void getRequest(String url, int statusCode) {
        given().
            auth().none().
        when().
            get(url).
        then().
            statusCode(statusCode);
    }

}
