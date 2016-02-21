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

package io.apiman.test.integration.rest.plugins.policies.corsplugin;

import static io.apiman.test.integration.runner.RestAssuredUtils.given;
import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;

import static org.hamcrest.Matchers.*;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;

import com.jayway.restassured.response.Response;
import org.junit.Test;

/**
 * @author jkaspar
 */
@Plugin(artifactId = "apiman-plugins-cors-policy")
public abstract class AbstractCORSPolicyIT extends AbstractApiTest {

    protected abstract String getResourceURL();

    protected abstract String getCorsValidationResourceURL();

    // Request related headers
    protected static final String ORIGIN_KEY = "Origin";
    protected static final String AC_REQUEST_METHOD_KEY = "Access-Control-Request-Method";
    protected static final String AC_REQUEST_HEADERS_KEY = "Access-Control-Request-Headers";

    // Response related headers
    protected static final String AC_ALLOW_ORIGIN_KEY = "Access-Control-Allow-Origin";
    protected static final String AC_ALLOW_CREDENTIALS_KEY = "Access-Control-Allow-Credentials";
    protected static final String AC_ALLOW_METHODS_KEY = "Access-Control-Allow-Methods";
    protected static final String AC_ALLOW_HEADERS_KEY = "Access-Control-Allow-Headers";
    protected static final String AC_EXPOSE_HEADERS_KEY = "Access-Control-Expose-Headers";
    protected static final String AC_MAX_AGE_KEY = "Access-Control-Max-Age";

    // Policy configuration
    protected static final String ALLOWED_ORIGIN_DOMAIN = "http://www.foo.com";
    protected static final String EXPOSE_HEADER = "exposed_header";
    protected static final String ALLOW_HEADER = "allowed_header";
    protected static final String ALLOW_METHOD = "GET";
    protected static final String MAX_AGE = "1600";
    

    protected Response doActualRequest() {
        return given()
            .header(ORIGIN_KEY, ALLOWED_ORIGIN_DOMAIN)
            .get(getResourceURL());
    }

    protected Response doPreflightRequest() {
        return given()
            .header(ORIGIN_KEY, ALLOWED_ORIGIN_DOMAIN)
            .header(AC_REQUEST_METHOD_KEY, ALLOW_METHOD)
            .header(AC_REQUEST_HEADERS_KEY, ALLOW_HEADER)
            .options(getResourceURL());
    }

    protected static void assertAuthorizationError(Response response) {
        response.then()
            .statusCode(400)
            .body("type", is("Authorization"));
    }

    @Test
    public void noCORSHeadersPresentWhenOriginHeaderIsNotSend() throws Exception {
        givenGateway().
             get(getResourceURL()).
        then().
            header(AC_ALLOW_ORIGIN_KEY, is(nullValue())).
            header(AC_ALLOW_CREDENTIALS_KEY, is(nullValue())).
            header(AC_ALLOW_METHODS_KEY, is(nullValue())).
            header(AC_ALLOW_HEADERS_KEY, is(nullValue())).
            header(AC_EXPOSE_HEADERS_KEY, is(nullValue())).
            header(AC_MAX_AGE_KEY, is(nullValue()));
    }

    @Test
    public void shouldHaveStatusCode200WhenRequestToPreflight() throws Exception {
        doPreflightRequest()
            .then().statusCode(200);
    }

    @Test
    public void shouldNotHaveResponseBodyWhenRequestToPreflight() throws Exception {
        doPreflightRequest()
            .then().body(isEmptyOrNullString());
    }

    @Test
    public void shouldHaveResponseBody() throws Exception {
        doActualRequest()
            .then().body(not(isEmptyOrNullString()));
    }

    @Test
    public void shouldHaveRequiredResponseHeaders() throws Exception {
        doActualRequest().then()
            .header(AC_ALLOW_ORIGIN_KEY, ALLOWED_ORIGIN_DOMAIN)
            .header(AC_EXPOSE_HEADERS_KEY, EXPOSE_HEADER)
            .header(AC_ALLOW_CREDENTIALS_KEY, "true");
    }

    @Test
    public void shouldNotContainPreflightHeadersWhenNotPreflightRequest() throws Exception {
        doActualRequest().then()
            .header(AC_ALLOW_METHODS_KEY, is(nullValue()))
            .header(AC_ALLOW_HEADERS_KEY, is(nullValue()))
            .header(AC_MAX_AGE_KEY, is(nullValue()));
    }

    @Test
    public void shouldContainPreflightHeadersWhenPreflightRequest() throws Exception {
        doPreflightRequest().then()
            .header(AC_ALLOW_ORIGIN_KEY, ALLOWED_ORIGIN_DOMAIN)
            .header(AC_ALLOW_CREDENTIALS_KEY, "true")
            .header(AC_ALLOW_METHODS_KEY, ALLOW_METHOD)
            .header(AC_ALLOW_HEADERS_KEY, ALLOW_HEADER)
            .header(AC_MAX_AGE_KEY, equalTo(MAX_AGE));
    }

    // CORS validation tests

    @Test
    public void shouldHaveStatusCode200WhenEverythingIsAllowed() throws Exception {
        given().header(ORIGIN_KEY, ALLOWED_ORIGIN_DOMAIN)
            .header(AC_REQUEST_METHOD_KEY, ALLOW_METHOD)
            .header(AC_REQUEST_HEADERS_KEY, ALLOW_HEADER)
            .options(getCorsValidationResourceURL())
            .then()
            .statusCode(200);
    }

    @Test
    public void shouldHaveStatusCode400WhenOriginIsNotAllowed() throws Exception {
        assertAuthorizationError(given()
            .header(ORIGIN_KEY, "notAllowedDomain")
            .header(AC_REQUEST_METHOD_KEY, ALLOW_METHOD)
            .options(getCorsValidationResourceURL()));
    }

    @Test
    public void shouldHaveStatusCode400WhenMethodIsNotAllowed() throws Exception {
        assertAuthorizationError(given()
            .header(ORIGIN_KEY, ALLOWED_ORIGIN_DOMAIN)
            .header(AC_REQUEST_METHOD_KEY, "POST")
            .options(getCorsValidationResourceURL()));
    }

    @Test
    public void shouldHaveStatusCode400WhenHeaderIsNotAllowed() throws Exception {
        assertAuthorizationError(given()
            .header(ORIGIN_KEY, ALLOWED_ORIGIN_DOMAIN)
            .header(AC_REQUEST_METHOD_KEY, ALLOW_METHOD)
            .header(AC_REQUEST_HEADERS_KEY, "notAllowedHeader")
            .options(getCorsValidationResourceURL()));
    }
}
