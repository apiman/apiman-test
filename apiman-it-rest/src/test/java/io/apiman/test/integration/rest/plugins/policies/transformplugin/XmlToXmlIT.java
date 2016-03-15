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

package io.apiman.test.integration.rest.plugins.policies.transformplugin;

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.entity.TestData;
import io.apiman.test.integration.categories.PluginTest;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.apis.EndpointContentType;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author opontes
 */
@Category({PolicyTest.class, PluginTest.class})
public class XmlToXmlIT extends AbstractTransformationIT {

    @ApiVersion(api = "api", endpoint = @Endpoint(value = DeployedServices.XML_DATA, contentType = EndpointContentType.xml),
        policies = @Policies(value = "plugins/transform_001", params = {"client", "XML", "server", "XML"}))
    private static ApiVersionBean apiVersionXmlToXml;

    @ManagedEndpoint("apiVersionXmlToXml")
    private String endpointXmlToXml;

    @Test
    public void shouldPassWhenCanTransformXmlToXml() throws IOException {
        TestData client = xmlToTestDataObject(getXmlFromTestService(DeployedServices.XML_DATA));
        TestData server = xmlToTestDataObject(getXmlFromGateway(endpointXmlToXml));
        Assert.assertNotNull(client);
        Assert.assertEquals(client, server);
    }
}
