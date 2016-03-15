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

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author opontes
 */
@Category({PolicyTest.class})
public class URLRewritingStringHeaderAndBodyIT extends AbstractURLRewritingPolicyIT {

    @ApiVersion(api = "api", endpoint = @Endpoint(value = DeployedServices.URL_REWRITING_DATA),
            policies = @Policies(value = "url_rewrite_001", params = {"regex", STRING_VALUE_UNCHANGED, "replace",
                    STRING_VALUE_CHANGED, "header", "true", "body", "true"}))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private String endpoint;

    @Before
    public void setUpValues() {
        setUpValues(endpoint);
    }

    @Test
    public void canRewriteHeaderAndBodyWithString() {
        Assert.assertTrue(changedHeaderValue.contains(STRING_VALUE_CHANGED));
        Assert.assertTrue(changedBodyValue.contains(STRING_VALUE_CHANGED));
    }
}
