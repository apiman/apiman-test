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

package io.apiman.test.integration.rest.plugins.policies.configplugin;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.categories.PluginTest;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({PluginTest.class, PolicyTest.class})
@Plugin(artifactId = "apiman-plugins-config-policy")
public class ConfigPluginApiPolicyIT extends AbstractApiTest {

    @ApiVersion(version = "1.0", api = "api")
    @ManagedEndpoint public static String basicEndpoint;

    @ApiVersion(version = "2.0", api = "api", policies = @Policies("config_001"))
    @ManagedEndpoint public static String pluginEndpoint;

    @Test
    public void shouldContainAddedRequestHeader() throws Exception {
        CommonTestMethods.requestContainsAddedHeaderTest(pluginEndpoint);
    }

    @Test
    public void shouldContainAddedResponseHeader() throws Exception {
        CommonTestMethods.responseContainsAddedHeaderTest(pluginEndpoint);
    }

    @Test
    public void shouldAddOnlySingleRequestHeader() throws Exception {
        CommonTestMethods.pluginAddedJustOneHeaderInRequestTest(basicEndpoint, pluginEndpoint);
    }

    @Test
    public void shouldAddOnlySingleResponseHeader() throws Exception {
        CommonTestMethods.pluginAddedJustOneHeaderInResponseTest(basicEndpoint, pluginEndpoint);
    }

}
