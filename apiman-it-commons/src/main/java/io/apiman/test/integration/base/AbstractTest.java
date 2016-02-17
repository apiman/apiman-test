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

import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.plans.PlanBean;

/**
 * This class is to be used as parent for arbitrary REST tests.
 * Child classes are provided with these resources:
 *    Organization,
 *    Api,
 *    Plan,
 *    Client
 *
 * Subclasses are required to create all version entities
 * @author jkaspar
 */
public abstract class AbstractTest extends AbstractApiTest {

    @Plan(organization = "organization")
    public static PlanBean plan;

    @Client(organization = "organization")
    public static ClientBean client;

}
