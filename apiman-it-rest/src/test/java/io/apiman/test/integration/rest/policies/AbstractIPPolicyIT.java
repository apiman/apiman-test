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

package io.apiman.test.integration.rest.policies;

import static io.apiman.test.integration.SuiteProperties.TOOL_PROXY_ADDRESS_PROP;
import static io.apiman.test.integration.SuiteProperties.TOOL_PROXY_PORT_PROP;
import static io.apiman.test.integration.runner.RestAssuredUtils.given;

import io.apiman.test.integration.SuiteProperties;
import io.apiman.test.integration.base.AbstractApiTest;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcechace
 */
public abstract class AbstractIPPolicyIT extends AbstractApiTest {

    // Logger
    public static final Logger LOG = LoggerFactory.getLogger(AbstractIPPolicyIT.class);

    // Proxy server
    private static String PROXY_PORT = SuiteProperties.getProperty(TOOL_PROXY_PORT_PROP);
    private static String PROXY_ADDRESS = SuiteProperties.getProperty(TOOL_PROXY_ADDRESS_PROP);

    private static HttpProxyServer proxy;


    @BeforeClass
    public static void startHttpProxy() throws UnknownHostException {
        proxy = DefaultHttpProxyServer.bootstrap()
            .withNetworkInterface(new InetSocketAddress(InetAddress.getByName(PROXY_ADDRESS), 0))
            .withAddress(new InetSocketAddress(InetAddress.getLoopbackAddress(), Integer.parseInt(PROXY_PORT)))
            .start();
    }

    @AfterClass
    public static void shutdownProxy() {
        if (proxy != null) {
            proxy.stop();
        }
    }

    public void getProxiedRequest(String url, int statusCode) {
        given().
            proxy(Integer.parseInt(PROXY_PORT)).
        when().
            get(url).
        then().
            statusCode(statusCode);
    }
}

