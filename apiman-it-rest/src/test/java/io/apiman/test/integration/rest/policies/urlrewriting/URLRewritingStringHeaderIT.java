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

import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author opontes
 */
public class URLRewritingStringHeaderIT extends AbstractURLRewritingPolicyIT {

    @ApiVersion(api = "api", endpoint = @Endpoint(value = ENDPOINT_DATA),
        policies = @Policies(value = "url_rewrite_001",
            params = {"regex", STRING_VALUE_UNCHANGED, "replace", STRING_VALUE_CHANGED, "header", "true", "body", "false" }))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private String endpoint;

    @Test
    public void shouldPassWhenRewritingHeaderIsSuccessful(){
        Map<String, String> originHeaders = getHeaders(DATA);
        Map<String, String> rewriteHeaders = getHeaders(endpoint);

        String originBody = getBody(DATA);
        String rewriteBody = getBody(endpoint);

        String originValue = originHeaders.get(HEADER_CHANGE);
        String rewriteValue = rewriteHeaders.get(HEADER_CHANGE);

        Assert.assertNotEquals(originValue, rewriteValue);
        Assert.assertEquals(originValue, STRING_VALUE_UNCHANGED);
        Assert.assertEquals(rewriteValue, STRING_VALUE_CHANGED);

        Assert.assertNotNull(originBody);
        Assert.assertEquals(originBody, rewriteBody);
    }
}
