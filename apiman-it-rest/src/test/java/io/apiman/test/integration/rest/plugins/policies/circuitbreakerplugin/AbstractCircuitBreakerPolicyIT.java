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

package io.apiman.test.integration.rest.plugins.policies.circuitbreakerplugin;

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import org.junit.Before;
import org.junit.Test;

import static io.apiman.test.integration.Suite.waitFor;
import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;
import static io.apiman.test.integration.runner.RestAssuredUtils.when;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by jsmolar.
 */
@Plugin(artifactId = "apiman-plugins-circuit-breaker-policy")
public abstract class AbstractCircuitBreakerPolicyIT extends AbstractApiTest {

    // Policy configuration
    protected static final String FAILURE_CODE = "503";
    protected static final String RESPONSE_ERROR_CODE = "598";
    protected static final String ENDPOINT = DeployedServices.DELAYED_RESPONSE + "/5000?status=598";

    protected abstract String getResourceURL();

    @Before
    public void setUp() {
        givenGateway().get(getResourceURL());
    }

    @Test
    public void shouldTripAfterReachLimit() {
        when().
            get(getResourceURL()).
        then().
            statusCode(Integer.parseInt(FAILURE_CODE)).
            time(lessThan(2L), SECONDS);
    }

    @Test
    public void shouldResetAfterTrip() {
        givenGateway().get(getResourceURL());

        waitFor(3000, "Waiting %d milliseconds to reset circuit");

        when().
            get(getResourceURL()).
        then().
            statusCode(Integer.parseInt(RESPONSE_ERROR_CODE)).
            time(greaterThanOrEqualTo(5L), SECONDS);
    }

}
