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
import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author opontes
 */
public class URLRewritingRegexHeaderAndBodyIT extends AbstractURLRewritingPolicyIT {

    @ApiVersion(api = "api", endpoint = @Endpoint(value = DeployedServices.URL_REWRITING_DATA),
            policies = @Policies(value = "url_rewrite_001",
                    params = {"regex", REGEX_EXPRESSION, "replace", REGEX_CHANGE, "header", "true", "body", "true"}))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private String endpoint;

    @Before
    public void setUpValues() {
        setUpValues(endpoint);
    }

    @Test
    public void canRewriteHeaderAndBodyWithRegex() {
        Assert.assertTrue(changedHeaderValue.contains(REGEX_CHANGE));
        Assert.assertTrue(changedBodyValue.contains(REGEX_CHANGE));
    }

}
