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

package io.apiman.test.integration.rest.policies.authorization;

import static io.apiman.test.integration.rest.policies.authorization.PolicyConstants.*;

import io.apiman.test.integration.base.AbstractApiTest;

import org.junit.Test;

/**
 * @author jcechace
 */
public abstract class AbstractAuthorizationPolicyIT extends AbstractApiTest {

    @Test
    public void shouldFailOnWildcardPath() {
        getRequest(getResourceURL(WILDCARD_PATH + "/foo"), SINGLE_ROLE, SINGLE_ROLE, 403);
    }

    @Test
    public void shouldPassOnWildcardPath() {
        getRequest(getResourceURL(WILDCARD_PATH + "/foo"), WILDCARD_ROLE, WILDCARD_ROLE, 200);
    }

    @Test
    public void shouldFailWhenNoMatch() {
        getRequest(getResourceURL(SINGLE_PATH), "nobody", "nobody", 403);
    }

    @Test
    public void shouldFailWhenNotAllMatch() {
        getRequest(getResourceURL(MULTI_PATH), SINGLE_ROLE, SINGLE_ROLE, 403);
    }

    @Test
    public void shouldPassWhenAllMatch() {
        getRequest(getResourceURL(MULTI_PATH), MULTI_ROLE_2, MULTI_ROLE_2, 200);
    }

    protected abstract String getResourceURL(String path);
}
