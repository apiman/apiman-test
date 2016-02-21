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

package io.apiman.test.integration.runner.restclients;

import static io.apiman.test.integration.runner.RestAssuredUtils.withManager;

import java.text.MessageFormat;

import com.jayway.restassured.response.Response;

/**
 * @author jcechace
 */
public abstract class AbstractRestWrapper {

    public static final String ACTION_PATH = "/actions";

    /**
     * Substitutes parameters in path template
     * e.g.: path("/organizations/{0}, "MyOrg") will be transformed to /organizations/MyOrg
     *
     * @param path resource path template
     * @param params template parameters
     * @return
     */
    protected static String path(String path, Object... params) {
        return MessageFormat.format(path, params);
    }

    /**
     * Performs POST request
     *
     * @param location request URL
     * @param entity payload object
     * @param entityClass type used to deserialize response payload
     * @param nullOnError whether return null in case of failure
     * @return object of specified response type
     */
    protected <ResponseEntity> ResponseEntity post(String location, Object entity, Class<ResponseEntity> entityClass,
        boolean nullOnError) {
        Response response = withManager().content(entity).post(location).prettyPeek();
        if (response.statusCode() != 200 && nullOnError) {
            return null;
        }
        return response.as(entityClass);
    }

    /**
     * Performs POST request
     *
     * @param location request URL
     * @param entity payload object
     * @param entityClass type used to deserialize response payload
     * @return object of specified response type
     */
    protected <ResponseEntity> ResponseEntity post(String location, Object entity, Class<ResponseEntity> entityClass) {
        return withManager().content(entity).post(location).as(entityClass);
    }

    /**
     * Performs POST request
     *
     * @param location request URL
     * @param entity payload object
     * @return response object
     */
    protected Response post(String location, Object entity) {
        return withManager().content(entity).post(location);
    }

    /**
     * Performs PUT request
     *
     * @param location request URL
     * @return object of specified response type
     */
    protected <ResponseEntity> ResponseEntity put(String location, Object entity, Class<ResponseEntity> entityClass) {
        return withManager().content(entity).put(location).as(entityClass);
    }

    /**
     * Performs PUT request
     *
     * @param location request URL
     * @param entity payload object
     * @return object of specified response type
     */
    protected Response put(String location, Object entity) {
        return withManager().content(entity).put(location);
    }

    /**
     * Performs GET request
     *
     * @param location request URL
     * @param entityClass type used to deserialize response payload
     * @return object of specified response type
     */
    protected <ResponseEntity> ResponseEntity get(String location, Class<ResponseEntity> entityClass) {
        return withManager().get(location).as(entityClass);
    }
}
