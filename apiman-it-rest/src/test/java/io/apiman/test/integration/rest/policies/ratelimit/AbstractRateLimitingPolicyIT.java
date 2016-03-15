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

package io.apiman.test.integration.rest.policies.ratelimit;

import static io.apiman.test.integration.runner.RestAssuredUtils.withGateway;

import static org.junit.Assert.assertTrue;

import io.apiman.test.integration.base.AbstractApiTest;

import java.util.concurrent.TimeUnit;

import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcechace
 */
public abstract class AbstractRateLimitingPolicyIT extends AbstractApiTest {

    // Logger
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractRateLimitingPolicyIT.class);

    // Custom HTTP header
    protected static final String HEADER_LIMIT = "X-RateLimit-Limit";
    protected static final String HEADER_REMAINING = "X-RateLimit-Remaining";
    protected static final String HEADER_RESET = "X-RateLimit-Reset";

    // Test attributes
    protected static final int LIMIT = 20;

    protected abstract String getResourceURL();

    @Before
    public void waitForLimitReset() throws InterruptedException {
        Response response = withGateway().get(getResourceURL());
        int waitFor = Integer.valueOf(response.header(HEADER_RESET)) + 1;
        LOG.info(String.format("Waiting %d seconds until period reset.", waitFor));
        TimeUnit.SECONDS.sleep(waitFor);
    }

    protected void getNRequests(int n, int statusCode) {
        for (int i = 0; i < n; i++) {
            getRequest(getResourceURL(), statusCode);
        }
    }

    protected void getNRequests(int n) {
        for (int i = 0; i < n; i++) {
            withGateway().get(getResourceURL());
        }
    }

    @Test
    public void shouldHaveValidResponseHeadersWithLimitInfo() {
        for (int i = 1; i <= LIMIT; i++ ) {
            Response response = withGateway().get(getResourceURL());
            response.then().
                    header(HEADER_LIMIT, String.valueOf(LIMIT)).
                    header(HEADER_REMAINING, String.valueOf(LIMIT - i));

            int reset = Integer.valueOf(response.getHeader(HEADER_RESET));
            assertTrue("Unexpected value in " + HEADER_RESET + " header", reset <= 60);
        }
    }

    @Test
    public void shouldPassWhenLimitIsNotExhausted() {
        getNRequests(LIMIT, 200);
    }

    @Test
    public void shouldFailWhenLimitIsExhausted() {
        getNRequests(LIMIT, 200);
        getRequest(getResourceURL(), 429);
    }

    @Test
    public void shouldPassWhenLimitResets() throws InterruptedException {
        getNRequests(LIMIT);
        waitForLimitReset();
        getRequest(getResourceURL(), 200);
    }
}
