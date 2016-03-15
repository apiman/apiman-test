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

package io.apiman.test.integration.rest.policies.transferquota;

import static io.apiman.test.integration.runner.RestAssuredUtils.*;

import static org.junit.Assert.assertTrue;

import io.apiman.test.integration.base.AbstractApiTest;

import java.util.concurrent.TimeUnit;

import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jkaspar
 */
public abstract class AbstractTransferQuotaPolicyIT extends AbstractApiTest {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTransferQuotaPolicyIT.class);

    // Http headers
    protected static final String HEADER_LIMIT = "X-TransferQuota-Limit";
    protected static final String HEADER_REMAINING = "X-TransferQuota-Remaining";
    protected static final String HEADER_RESET = "X-TransferQuota-Reset";

    // Policy configuration
    protected static final int LIMIT = 10; // in bytes

    protected abstract String getResourceURL();

    protected abstract Response useUpNBytes(int n);

    protected Response uploadNBytes(int n) {
        return  with().body(new byte[n]).post(getResourceURL());
    }

    protected Response downloadNBytes(int n) {
        return with().header("X-download-n-bytes", n).get(getResourceURL());
    }

    protected void assertRemaining(int remaining) {
        givenGateway().
            get(getResourceURL()).
        then().
            header(HEADER_REMAINING, String.valueOf(remaining));
    }

    @Before
    public void waitForLimitReset() throws InterruptedException {
        Response response = withGateway().get(getResourceURL());
        int waitFor = Integer.valueOf(response.header(HEADER_RESET)) + 1;
        LOG.info(String.format("Waiting %d seconds until period reset.", waitFor));
        TimeUnit.SECONDS.sleep(waitFor);
    }

    @Test
    public void shouldHaveValidResponseHeadersWithLimitInfo() {
        for (int i = 1; i <= LIMIT; i++) {
            useUpNBytes(1);
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
        for (int i = 0; i < LIMIT; i++) {
            useUpNBytes(1).then().statusCode(200);
        }
    }

    @Test
    public void shouldFailWhenLimitIsExhausted() {
        useUpNBytes(LIMIT);
        useUpNBytes(1).then().statusCode(429);
    }

    @Test
    public void shouldPassWhenLimitResets() throws InterruptedException {
        useUpNBytes(LIMIT);
        waitForLimitReset();
        useUpNBytes(1).then().statusCode(200);
    }
}
