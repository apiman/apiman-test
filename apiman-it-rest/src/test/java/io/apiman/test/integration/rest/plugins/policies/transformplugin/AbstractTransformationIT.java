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

package io.apiman.test.integration.rest.plugins.policies.transformplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.base.entity.TestData;
import io.apiman.test.integration.runner.annotations.entity.Plugin;

import java.io.IOException;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;
import static io.apiman.test.integration.runner.RestAssuredUtils.givenTestServices;

/**
 * @author opontes
 */
@Plugin(artifactId = "apiman-plugins-transformation-policy")
public abstract class AbstractTransformationIT extends AbstractApiTest {

    protected String getJsonFromTestService(String link) {
        return givenTestServices().
                   accept(ContentType.JSON).
               when().
                   get(link).
               then().
                   contentType(ContentType.JSON).
               extract().
                   body().
                   jsonPath().
                   prettyPrint();
    }

    protected String getJsonFromGateway(String link) {
        return givenGateway().
                   accept(ContentType.JSON).
               when().
                   get(link).
               then().
                   contentType(ContentType.JSON).
               extract().
                   body().
                   jsonPath().
                   prettyPrint();
    }

    protected void postJsonToGateway(String link, String json){
        givenGateway().
            contentType(ContentType.JSON).
            body(json).
        when().
            post(link).
        then().
            assertThat().
            statusCode(200);
    }

    protected String postJsonToGateway(String link, String json, String o){
        return givenGateway().
            contentType(ContentType.JSON).
            body(json).
            when().
            post(link).
            body().prettyPrint().toString();
    }

    protected void postXMLToGateway(String link, String xml){
        givenGateway().
            contentType(ContentType.XML).
            body(xml).
        when().
            post(link).
        then().
            assertThat().
            statusCode(200);
    }

    protected String postXMLToGateway(String link, String xml, String o){
        return givenGateway().
            contentType(ContentType.XML).
            body(xml).
            when().
            post(link).
            prettyPrint();
    }

    protected String getXmlFromTestService(String link) {
        return givenTestServices().
                   accept("application/xml").
               when().
                   get(link).
               then().
                   contentType(ContentType.XML).
               extract().
                   body().
                   xmlPath().
                   prettyPrint();
    }

    protected String getXmlFromGateway(String link) {
        return givenGateway().
                   accept("application/xml").
               when().
                   get(link).
               then().
                   contentType(ContentType.XML).
               extract().
                   body().
                   xmlPath().
                   prettyPrint();
    }

    protected TestData jsonToTestDataObject(String json) throws IOException {
        return new ObjectMapper().readValue(json, TestData.class);
    }

    protected TestData xmlToTestDataObject(String xml) throws IOException {
        return new XmlMapper().readValue(xml, TestData.class);
    }
}
