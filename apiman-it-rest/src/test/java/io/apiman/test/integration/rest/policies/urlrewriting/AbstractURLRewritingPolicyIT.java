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

package io.apiman.test.integration.rest.policies.urlrewriting;

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.AbstractApiTest;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;
import static io.apiman.test.integration.runner.RestAssuredUtils.givenTestServices;

/**
 * @author opontes
 */
public abstract class AbstractURLRewritingPolicyIT extends AbstractApiTest {

    public static final String STRING_VALUE_UNCHANGED = "I want to change";
    public static final String STRING_VALUE_CHANGED = "I am changed";
    public static final String STRING_HEADER_NAME = "Change";

    public static final String REGEX_EXPRESSION = "\\\\w+(?=\\\\/)";
    public static final String REGEX_CHANGE = "XXXXXX";
    public static final String REGEX_HEADER_NAME = "URLRewrite";

    protected String originalHeaderValue;
    protected String changedHeaderValue;
    protected String originalBodyValue;
    protected String changedBodyValue;

    protected void setUpValues(String endpoint, String header) {
        originalHeaderValue = getHeadersFromTestServices(DeployedServices.URL_REWRITING_DATA).get(header);
        changedHeaderValue = getHeadersFromGateway(endpoint).get(header);

        originalBodyValue = getBodyFromTestServices(DeployedServices.URL_REWRITING_DATA);
        changedBodyValue = getBodyFromGateway(endpoint);

        if(STRING_HEADER_NAME.equals(header)){
            Assert.assertTrue(originalHeaderValue.contains(STRING_VALUE_UNCHANGED));
            Assert.assertTrue(originalBodyValue.contains(STRING_VALUE_UNCHANGED));
        }
    }


    private Map<String, String> getHeadersFromTestServices(String link) {
        final Map<String, String> headers = new HashMap<>();
        givenTestServices().get(link).getHeaders().forEach(x -> headers.put(x.getName(), x.getValue()));
        return headers;
    }

    private Map<String, String> getHeadersFromGateway(String link) {
        final Map<String, String> headers = new HashMap<>();
        givenGateway().get(link).getHeaders().forEach(x -> headers.put(x.getName(), x.getValue()));
        return headers;
    }

    private String getBodyFromTestServices(String link) {
        return givenTestServices().get(link).body().prettyPrint();
    }

    private String getBodyFromGateway(String link) {
        return givenGateway().get(link).body().prettyPrint();
    }
}
