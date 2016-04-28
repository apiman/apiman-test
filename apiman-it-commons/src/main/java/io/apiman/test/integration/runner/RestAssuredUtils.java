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

package io.apiman.test.integration.runner;

import static io.apiman.test.integration.SuiteProperties.APIMAN_MANAGER_PASSWORD;
import static io.apiman.test.integration.SuiteProperties.APIMAN_MANAGER_USER;

import static com.jayway.restassured.RestAssured.preemptive;

import io.apiman.test.integration.Suite;
import io.apiman.test.integration.SuiteProperties;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.NoAuthScheme;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * Rest-Assured  configuration provider
 *
 * @author jcechace
 */
public class RestAssuredUtils {

    private static String MANAGER_USER = SuiteProperties.getProperty(APIMAN_MANAGER_USER);
    private static String MANAGER_PASS = SuiteProperties.getProperty(APIMAN_MANAGER_PASSWORD);

    /*
        GENERAL REST-ASSURED CONFIGURATION
    */
    static {
        RestAssured. enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /*
        REQUEST SPECIFICATION
     */

    /**
     * Default RequestSpecification configuration to be used as base for others
     */
    public static RequestSpecification DEFAULT_TEST_SPEC = new RequestSpecBuilder()
        .setAuth(new NoAuthScheme())
        .setRelaxedHTTPSValidation()
        .log(LogDetail.PATH)
        .log(LogDetail.METHOD)
        .build();

    /**
     * RequestSpecification configuration for communication with Apiman Manager
     */
    public static RequestSpecification MANAGER_SPEC = new RequestSpecBuilder()
        .addRequestSpecification(DEFAULT_TEST_SPEC)
        .setAuth(preemptive().basic(MANAGER_USER, MANAGER_PASS))
        .setContentType(ContentType.JSON)
        .setAccept(ContentType.JSON)
        .setBaseUri(Suite.getManagerUrl())
        .build();

    /**
     * RequestSpecification configuration for communication with Apiman Gateway
     */
    public static RequestSpecification GATEWAY_SPEC = new RequestSpecBuilder()
        .addRequestSpecification(DEFAULT_TEST_SPEC)
        .build();

    /**
     * RequestSpecification configuration for communication with deployment services
     */
    public static RequestSpecification TEST_SERVICES_SPEC = new RequestSpecBuilder()
        .addRequestSpecification(DEFAULT_TEST_SPEC)
        .setBaseUri(Suite.getDeploymentUrl())
        .build();

    /*
        MANAGER REQUEST STARTERS
    */

    /**
     * Manager alternative to {@link RestAssured#given()}
     * Equal to {@code calling RestAssured.given().spec(MANAGER_SPEC)}
     *
     * @return the request specification
     */
    public static RequestSpecification givenManager() {
        return RestAssured.given().spec(MANAGER_SPEC);
    }

    /**
     * Manager alternative to {@link RestAssured#with()}
     * Redirects to {@link RestAssuredUtils#givenManager()} ()}
     *
     * @return the request specification
     */
    public static RequestSpecification withManager() {
        return givenManager();
    }

    /*
        GATEWAY REQUEST STARTERS
    */

    /**
     * Gateway alternative to {@link RestAssured#given()}
     * Equal to {@code calling RestAssured.given().spec(GATEWAY_SPEC)}
     *
     * @return the request specification
     */
    public static RequestSpecification givenGateway() {
        return RestAssured.given().spec(GATEWAY_SPEC);
    }

    /**
     * Gateway alternative to {@link RestAssured#with()}
     * Redirects to {@link RestAssuredUtils#givenGateway()} ()}
     *
     * @return the request specification
     */
    public static RequestSpecification withGateway() {
        return givenGateway();
    }

    /*
        TEST SERVICES REQUEST STARTERS
    */

    /**
     * Gateway alternative to {@link RestAssured#given()}
     * Equal to {@code calling RestAssured.given().spec(TEST_SERVICES_SPEC)}
     *
     * @return the request specification
     */
    public static RequestSpecification givenTestServices() {
        return RestAssured.given().spec(TEST_SERVICES_SPEC);
    }

    /**
     * Gateway alternative to {@link RestAssured#with()}
     * Redirects to {@link RestAssuredUtils#givenTestServices()} ()}
     *
     * @return the request specification
     */
    public static RequestSpecification withTestServices() {
        return givenTestServices();
    }

    /*
        DEFAULT REQUEST STARTERS
    */

    /**
     * Default alternative to {@link RestAssured#given()}
     * Redirects to {@link RestAssuredUtils#withGateway()}
     *
     * @return the request specification
     */
    public static RequestSpecification given() {
        return withGateway();
    }

    /**
     * Default alternative to {@link RestAssured#with()}
     * Redirects to {@link RestAssuredUtils#given()}
     *
     * @return the request specification
     */
    public static RequestSpecification with() {
        return given();
    }

    /**
     * Default alternative to {@link RestAssured#when()}
     * Redirects to {@link RestAssuredUtils#given()}
     *
     * @return the request specification
     */
    public static RequestSpecification when() {
        return given();
    }
}
