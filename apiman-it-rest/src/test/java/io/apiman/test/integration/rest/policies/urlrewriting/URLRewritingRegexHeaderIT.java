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

import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author opontes
 */
public class URLRewritingRegexHeaderIT extends AbstractURLRewritingPolicyIT {

    @ApiVersion(api = "api", endpoint = @Endpoint(value = ENDPOINT_DATA),
            policies = @Policies(value = "url_rewrite_001",
                    params = {"regex", REGEX_EXPRESSION, "replace", REGEX_CHANGE, "header", "true", "body", "false" }))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private String endpoint;

    @Test
    public void shouldPassWhenRewritingHeaderIsSuccessful(){
        Map<String, String> originHeaders = getHeaders(DATA);
        Map<String, String> rewriteHeaders = getHeaders(endpoint);

        String originBody = getBody(DATA);
        String rewriteBody = getBody(endpoint);

        String originValue = originHeaders.get(REGEX_HEADER_NAME);
        String rewriteValue = rewriteHeaders.get(REGEX_HEADER_NAME);

        Assert.assertNotEquals(originValue, rewriteValue);
        Assert.assertTrue(!originValue.contains(REGEX_CHANGE));
        Assert.assertTrue(rewriteValue.contains(REGEX_CHANGE));

        Assert.assertNotNull(originBody);
        Assert.assertEquals(originBody, rewriteBody);
    }
}
