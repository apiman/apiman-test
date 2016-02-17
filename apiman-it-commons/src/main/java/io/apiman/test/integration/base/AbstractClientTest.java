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

import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

/**
 * This class is to be used as parent for clientApp focused REST tests
 * Child classes are provided with these resources:
 *    Organization,
 *    Api + version,
 *    Plan + version,
 *    Client
 *
 * @author jkaspar
 */
public abstract class AbstractClientTest extends AbstractTest {

    @PlanVersion(plan = "plan")
    public static PlanVersionBean planVersion;

    @ApiVersion(api = "api", vPlans = {"planVersion"})
    public static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    public static String endpoint;

}
