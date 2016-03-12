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

package io.apiman.test.integration.rest.plugins.policies.keycloakplugin;

import static io.apiman.test.integration.runner.RestAssuredUtils.given;
import static io.apiman.test.integration.runner.RestAssuredUtils.when;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.categories.PluginTest;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.rest.support.KCTokenProvider;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mjaros
 */
@Category({PolicyTest.class, PluginTest.class})
@Plugin(artifactId = "apiman-plugins-keycloak-oauth-policy")
public class KeycloakAuthenticationPolicyIT extends AbstractApiTest {

    public static Logger LOG = LoggerFactory.getLogger(KeycloakAuthenticationPolicyIT.class);
    public static long TOKEN_TIMEOUT_SLEEP = 40;

    @ApiVersion(api = "api", policies = @Policies("authentication_kc_001"))
    private static ApiVersionBean apiVersion;

    @ManagedEndpoint("apiVersion")
    private String endpoint;

    @Test
    public void canAuthenticateViaParameter() {
        String token = KCTokenProvider.getAccessToken("admin", "admin");
        given().
            queryParam("access_token",  token).
        when().
            get(endpoint).
        then().
            statusCode(200);
    }

    @Test
    public void canAuthenticateViaHeader() {
        String token = KCTokenProvider.getAccessToken("user", "user");
        given().
            auth().none().
            header("Authorization", "Bearer " + token).
        when().
            get(endpoint).
        then().
            statusCode(200);
    }

    @Test
    public void shouldFailWhenTokenIsNotProvided() {
        when().
            get(endpoint).
        then().
            statusCode(401);
    }

    @Test
    public void shouldFailWhenOldTokenIsProvided() throws InterruptedException {
        String token = KCTokenProvider.getAccessToken("nobody", "nobody");
        LOG.info("Waiting " + TOKEN_TIMEOUT_SLEEP  + " for token to expire");
        TimeUnit.SECONDS.sleep(TOKEN_TIMEOUT_SLEEP);

        given().
            auth().none().
            queryParam("access_token", token).
        when().
            get(endpoint).
        then().
            statusCode(401);
    }
}
