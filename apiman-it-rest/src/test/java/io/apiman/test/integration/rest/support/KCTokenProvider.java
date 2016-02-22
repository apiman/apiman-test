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

package io.apiman.test.integration.rest.support;

import static io.apiman.test.integration.SuiteProperties.*;
import static io.apiman.test.integration.runner.RestAssuredUtils.with;

import io.apiman.test.integration.SuiteProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KCTokenProvider {

    public static final String REALM_CERT;
    public static final String TOKEN_ADDR = "/protocol/openid-connect/token";

    static {
        try {
            Path crt = Paths.get(KCTokenProvider.class.getResource("/kcRealm.crt").toURI());
            REALM_CERT = new String(Files.readAllBytes(crt));
        } catch (URISyntaxException | IOException e) {
            throw new IllegalArgumentException("Unable to find KC Realm certificate", e);
        }
    }

    private static String getKcAuthUrl() {
        String address = SuiteProperties.getProperty(TOOL_KC_ADDRESS_PROP);
        String port = SuiteProperties.getProperty(TOOL_KC_PORT_PROP);
        String realm = SuiteProperties.getProperty(TOOL_KC_REALM_PROP);
        return "http://" + address + ":" + port + "/auth/realms/" + realm + TOKEN_ADDR;
    }


    public static String getAccessToken(String username, String password) {
        return  with().
                    contentType("application/x-www-form-urlencoded").
                    authentication().none().
                    formParam("username", username).
                    formParam("password", password).
                    formParam("grant_type", "password").
                    formParam("client_id", "qeclient").
                post(getKcAuthUrl()).
                    body().
                    path("access_token");
    }
}
