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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.apis.EndpointContentType;
import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.entity.TestData;
import io.apiman.test.integration.base.entity.TestDataRoot;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.get;
import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;

/**
 * @author opontes
 */
public class XmlToJsonIT extends AbstractTransformationIT {

    @ApiVersion(api = "api", endpoint = @Endpoint(value = DeployedServices.XML_DATA, contentType = EndpointContentType.xml),
        policies = @Policies(value = "plugins/transform_001", params = {"client", "JSON", "server", "XML"}))
    private static ApiVersionBean apiVersionXmlToJson;

    @ManagedEndpoint("apiVersionXmlToJson")
    private String endpointXmlToJson;

    @Test
    public void shouldPassWhenCanTransformXmlToJson() throws IOException {
        TestData client = xmlToTestDataObject(getXmlFromTestService(DeployedServices.XML_DATA));
        TestDataRoot server = jsonToTestDataRootObject(getJsonFromGateway(endpointXmlToJson));

        Assert.assertNotNull(client);
        Assert.assertEquals(client, server.getTestData());
    }

    protected TestDataRoot jsonToTestDataRootObject(String json) throws IOException {
        return new ObjectMapper().readValue(json, TestDataRoot.class);

    }

    @Override
    protected String getJsonFromGateway(String link) {
        return givenGateway().get(link)
                   .body().asString();
    }
}
