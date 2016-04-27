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

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import org.junit.Test;

import static io.apiman.test.integration.runner.RestAssuredUtils.when;
import static org.hamcrest.Matchers.*;

/**
 * Created by jsmolar.
 */
@Plugin(artifactId = "apiman-plugins-http-security-policy")
public abstract class AbstractHttpSecurityHSTSPolicyIT extends AbstractApiTest {

    protected static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    protected static final String X_FRAME_OPTIONS = "X-Frame-Options";
    protected static final String X_XSS_PROTECTION = "X-XSS-Protection";
    protected static final String X_CONTENT_TYPE = "X-Content-Type-Options";

    protected static final String CONTENT_SECURITY = "Content-Security-Policy";
    protected static final String CONTENT_SECURITY_REPORT_ONLY = "Content-Security-Policy-Report-Only";

    protected abstract String getResourceURL();

    protected abstract String getHstsDisabledResourceURL();

    @Test
    public void shouldNotContainContentSecurityPolicyHeader(){
        when().
            get(getResourceURL()).
        then().
            header(CONTENT_SECURITY, is(isEmptyOrNullString())).
            header(CONTENT_SECURITY_REPORT_ONLY, is(isEmptyOrNullString()));
    }

    @Test
    public void shouldNotContainXFrameOptionsHeader(){
        when().
            get(getResourceURL()).
        then().
            header(X_CONTENT_TYPE, is(isEmptyOrNullString()));
    }

    @Test
    public void shouldNotContainsXXSSProtectionHeader(){
        when().
            get(getResourceURL()).
        then().
            header(X_XSS_PROTECTION, is(isEmptyOrNullString()));
    }

    @Test
    public void shouldNotContainsXFrameOptionsHeader(){
        when().
            get(getResourceURL()).
        then().
            header(X_FRAME_OPTIONS, is(isEmptyOrNullString()));
    }

    @Test
    public void shouldContainsCompleteHSTSHeader(){
        when().
            get(getResourceURL()).
        then().
            header(STRICT_TRANSPORT_SECURITY, is(containsString("max-age=13"))).
            header(STRICT_TRANSPORT_SECURITY, is(containsString("includeSubDomains"))).
            header(STRICT_TRANSPORT_SECURITY, is(containsString("preload")));
    }

    @Test
    public void shouldNotAddStrictTransportToHeader(){
        when().
            get(getHstsDisabledResourceURL()).
        then().
            header(STRICT_TRANSPORT_SECURITY, is(isEmptyOrNullString()));
    }

}
