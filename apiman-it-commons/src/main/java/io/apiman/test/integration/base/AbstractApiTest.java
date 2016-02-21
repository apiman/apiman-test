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

package io.apiman.test.integration.base;

import static io.apiman.test.integration.runner.RestAssuredUtils.given;
import static io.apiman.test.integration.runner.RestAssuredUtils.when;

import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.RestAssuredUtils;
import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * This class is to be used as parent for api focused REST tests.
 * Child classes are provided with these resources:
 *    Organization,
 *    Api
 *
 * @author jcechace
 */
@RunWith(ApimanRunner.class)
public abstract class AbstractApiTest {

    @Organization()
    public static OrganizationBean organization;

    @Api(organization = "organization")
    public static ApiBean api;


    /*
     * Convenience methods for basic POST and GET requests
     */
    public void getRequest(String url, int statusCode) {
        when().
            get(url).
        then().
            statusCode(statusCode);
    }


    protected void getRequest(String url, String username, String password, int statusCode) {
        given().
            auth().basic(username, password).
        when().
            get(url).
        then().
            statusCode(statusCode);
    }

    public void postRequest(String url, int statusCode) {
        when().
            post(url).
        then().log().body(true).
            statusCode(statusCode);
    }

    /**
     * Add apikey parameter to given url
     * @param url url
     * @param apiKey value of apikey parameter
     * @return url with apikey parameter
     */
    public static String addApiKeyParameter(String url, String apiKey) {
        return url + (url.contains("?") ? "&" : "?") + "apikey=" + apiKey;
    }
}
