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

package io.apiman.test.integration.rest.plugins.policies.httpsecurityplugin;

import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.test.integration.categories.PluginTest;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import org.junit.experimental.categories.Category;

/**
 * Created by jsmolar.
 */
@Category({PolicyTest.class, PluginTest.class})
public class HttpSecurityCSPApiPolicyIT extends AbstractHttpSecurityCSPPolicyIT {

    @ApiVersion(api = "api",
            policies = @Policies(value = "plugins/httpsecurity/http_security_csp",
                params = {"csp.mode", "ENABLED", "csp", CSP_CONFIG}))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private static String endpoint;

    @ApiVersion(api = "api", version = "cspReportOnly",
            policies = @Policies(value = "plugins/httpsecurity/http_security_csp",
                params = {"csp.mode", "REPORT_ONLY", "csp", CSP_CONFIG}))
    private static ApiVersionBean cspReportOnlyApiVersion;

    @ManagedEndpoint("cspReportOnlyApiVersion")
    private static String cspReportOnlyEndpoint;

    @ApiVersion(api = "api", version = "cspDisabled",
            policies = @Policies(value = "plugins/httpsecurity/http_security_csp",
                params = {"csp.mode", "DISABLED", "csp", CSP_CONFIG}))
    private static ApiVersionBean cspDisabledApiVersion;

    @ManagedEndpoint("cspDisabledApiVersion")
    private static String cspDisabledEndpoint;

    @Override
    protected String getResourceURL() {
        return endpoint;
    }

    @Override
    protected String getCspReportOnlyResourceURL() {
        return cspReportOnlyEndpoint;
    }

    @Override
    protected String getCspDisabledResourceURL() {
        return cspDisabledEndpoint;
    }

}
