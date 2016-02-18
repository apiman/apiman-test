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

import static com.jayway.restassured.RestAssured.get;

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.AbstractApiTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author opontes
 */
public abstract class AbstractURLRewritingPolicyIT extends AbstractApiTest{


    public static final String ENDPOINT_DATA = DeployedServices.RESPONSE_REST_ROOT + "/URLRewritingData";
    public static final String DATA = DeployedServices.LOCALHOST + ENDPOINT_DATA;
    public static final String STRING_VALUE_UNCHANGED = "I want to change";
    public static final String STRING_VALUE_CHANGED = "I am changed";
    public static final String REGEX_EXPRESSION = "(\\\\/orgs\\\\/TestOrg)\\\\d+";
    public static final String REGEX_CHANGE = "xxxxxxxxxxxxxxx";
    public static final String REGEX_HEADER_NAME = "URLRewrite";
    public static final String HEADER_CHANGE = "Change";
    public static final String HEADER_REWRITE = "URLRewrite";

    protected Map<String, String> getHeaders(String link){
        final Map<String, String> headers = new HashMap<>();
        get(link).getHeaders().forEach(x -> headers.put(x.getName(), x.getValue()));
        return headers;
    }

    protected String getBody(String link){
        return get(link).body().prettyPrint();
    }
}
