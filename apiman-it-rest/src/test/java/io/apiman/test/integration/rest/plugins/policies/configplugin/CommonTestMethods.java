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

package io.apiman.test.integration.rest.plugins.policies.configplugin;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import com.jayway.restassured.path.json.JsonPath;

/**
 * @author jkaspar
 */
public class CommonTestMethods {

    public static void requestContainsAddedHeaderTest(String endpoint) throws Exception {
        when()
            .get(endpoint)
        .then()
            .statusCode(200)
            .body("headers.request-header", equalTo("true"));
    }

    public static void responseContainsAddedHeaderTest(String endpoint) throws Exception {
        when()
            .get(endpoint)
        .then()
            .statusCode(200)
            .header("response-header", equalTo("true"));
    }

    public static void pluginAddedJustOneHeaderInResponseTest(String basicEndpoint, String pluginEndpoint) throws Exception {
        int basicHeaders = get(basicEndpoint).getHeaders().size();
        int pluginHeaders = get(pluginEndpoint).getHeaders().size();
        assertEquals("Unexpected number of headers", basicHeaders + 1, pluginHeaders);
    }

    public static void pluginAddedJustOneHeaderInRequestTest(String basicEndpoint, String pluginEndpoint) throws Exception {
        String json = get(basicEndpoint).asString();
        JsonPath jsonPath = new JsonPath(json);
        int basicHeaders = jsonPath.getInt("headers.size()");

        when()
            .get(pluginEndpoint)
        .then()
            .body("headers.size()", equalTo(basicHeaders + 1));
    }
}
