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
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;

/**
 * Created by jsmolar.
 */
public class HttpSecurityHSTSApiPolicyIT extends AbstractHttpSecurityHSTSPolicyIT{

    @ApiVersion(api = "api",
            policies = @Policies(value = "plugins/httpsecurity/http_security_hsts",
                    params = {"enabled", "true", "subdomains", "true", "maxAge", "13", "preload", "true"}))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private static String endpoint;

    @ApiVersion(api = "api", version = "hstsDisabled",
            policies = @Policies(value = "plugins/httpsecurity/http_security_hsts",
                    params = {"enabled", "false", "subdomains", "true", "maxAge", "13", "preload", "true"}))
    private static ApiVersionBean hstsDisabledapiVersion;

    @ManagedEndpoint("hstsDisabledapiVersion")
    private static String hstsDisabledendpoint;

    @Override
    protected String getResourceURL() {
        return endpoint;
    }

    @Override
    protected String getHstsDisabledResourceURL(){
        return hstsDisabledendpoint;
    }

}
