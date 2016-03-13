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

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;
import static io.apiman.test.integration.runner.RestAssuredUtils.givenTestServices;

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.AbstractApiTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

/**
 * @author opontes
 */
public abstract class AbstractURLRewritingPolicyIT extends AbstractApiTest {

    public static final String STRING_VALUE_UNCHANGED = "example.com";
    public static final String STRING_VALUE_CHANGED = "redhat.com/en";

    public static final String REGEX_EXPRESSION = "\\\\w+(?=\\\\/)";
    public static final String REGEX_CHANGE = "XXXXXX";

    public static final String HEADER_NAME = "URLRewrite";

    protected String originalHeaderValue;
    protected String changedHeaderValue;
    protected String originalBodyValue;
    protected String changedBodyValue;

    protected void setUpValues(String endpoint) {
        originalHeaderValue = getHeadersFromTestServices(DeployedServices.URL_REWRITING_DATA).get(HEADER_NAME);
        changedHeaderValue = getHeadersFromGateway(endpoint).get(HEADER_NAME);

        originalBodyValue = getBodyFromTestServices(DeployedServices.URL_REWRITING_DATA);
        changedBodyValue = getBodyFromGateway(endpoint);

        Assert.assertTrue(originalHeaderValue.contains(STRING_VALUE_UNCHANGED));
        Assert.assertTrue(originalBodyValue.contains(STRING_VALUE_UNCHANGED));
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
