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

import static com.jayway.restassured.RestAssured.preemptive;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * Rest-Assured  configuration provider
 *
 * @author jcechace
 */
public class RestAssuredConfig {

    public static final String APIMAN_HOST = System.getProperty("test_serverUrl", "localhost");
    public static final String APIMAN_PORT = System.getProperty("test_consolePort", "8080");
    public static final String APIMAN_PROTOCOL = System.getProperty("test_consoleProtocol", "http");
    public static final String APIMAN_USER = System.getProperty("test_admin_username", "admin");
    public static final String APIMAN_PWD = System.getProperty("test_admin_password", "admin123!");

    public static final String BASE_URI = String.format("%s://%s", APIMAN_PROTOCOL, APIMAN_HOST);

    /**
     * Initialize default RestAssured configuration to be used with Apiman
     *
     * apiman.keystore system property is required
     */
    public static void init() {
        KeyStore keyStore;
        try {
            Path keyStorePath = Paths.get(System.getProperty("apiman.keystore"));
            keyStore = KeyStore.getInstance("jks");
            keyStore.load(Files.newInputStream(keyStorePath), "secret".toCharArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load apiman keystore");
        }

        // Create default request specification;
        RequestSpecification spec = new RequestSpecBuilder()
            .setAuth(preemptive().basic(APIMAN_USER, APIMAN_PWD))
            .setTrustStore(keyStore)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .setBaseUri(BASE_URI)
            .setBasePath("/apiman")
            .setPort(Integer.parseInt(APIMAN_PORT))
            .log(LogDetail.METHOD)
            .log(LogDetail.PATH)
            .build();

        // Set request logging
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // Set default request specification
        RestAssured.requestSpecification = spec;
        // Set default parser
        RestAssured.defaultParser = Parser.JSON;
    }
}
