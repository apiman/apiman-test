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

package io.apiman.test.integration.rest.policies.ipwhitelist;

import io.apiman.test.integration.rest.policies.AbstractIPPolicyIT;

import org.junit.Test;

/**
 * @author jcechace
 */
public abstract class AbstractWhitelistPolicyIT extends AbstractIPPolicyIT {

    protected abstract String getLoopbackResourceURL();

    protected abstract String getProxyResourceURL();

    @Test
    public void shouldPassWhenGetRequestToLoopbackFromLoopback() {
        getRequest(getLoopbackResourceURL(), 200);
    }

    @Test
    public void shouldPassWhenGetRequestToProxyFromProxy() {
        getProxiedRequest(getProxyResourceURL(), 200);
    }

    @Test
    public void shouldFailWHenGetRequestToLoopbackFromProxy() {
        getProxiedRequest(getLoopbackResourceURL(), 500);
    }

    @Test
    public void shouldFailWHenGetRequestToProxyFromLoopback() {
        getRequest(getProxyResourceURL(), 500);
    }
}
