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

import com.jayway.restassured.specification.ResponseSpecification;

/**
 * Client used to operate on apiman resources such s Organizations, Apis, Applications and Plans
 *
 * @author jcechace
 */
public interface EntityRestClient<Entity, NewEntity> {

    /**
     * Initializes client by retrieving entity via REST API by id or version
     * @param idOrVersion id or version
     * @return this client
     */
    EntityRestClient<Entity, NewEntity> fetch(String idOrVersion);

    /**
     * Initializes client by creating new entity via REST API
     * @param newBean bean used for POST request
     * @return this client
     */
    EntityRestClient<Entity, NewEntity> create(NewEntity newBean);

    /**
     * Perform DELETE request via REST API by id or version
     * @param idOrVersion id or version
     * @param spec specification to be used for response verification
     */
    void delete(String idOrVersion, ResponseSpecification spec);

    /**
     * Perform GET request and verify the response
     * @param idOrVersion id or version
     * @param spec specification to be used for response verification
     */
    void peek(String idOrVersion, ResponseSpecification spec);

    /**
     * Perform GET request and verify the existence of entity
     * @param idOrVersion id or version
     */
    void peek(String idOrVersion);

    /**
     * Return created or retrieved entity
     * @return entity
     */
    Entity getBean();
}
