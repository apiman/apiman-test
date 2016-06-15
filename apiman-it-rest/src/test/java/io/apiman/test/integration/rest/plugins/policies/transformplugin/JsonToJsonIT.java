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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import io.apiman.test.integration.DeployedServices;
import io.apiman.test.integration.base.entity.TestData;
import io.apiman.test.integration.categories.PluginTest;
import io.apiman.test.integration.categories.PolicyTest;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author opontes
 */
@Category({PolicyTest.class, PluginTest.class})
public class JsonToJsonIT extends AbstractTransformationIT {
    @ApiVersion(api = "api", endpoint = @Endpoint(value = DeployedServices.JSON_DATA),
            policies = @Policies(value = "plugins/transform_001", params = {"client", "JSON", "server", "JSON"}))
    private static ApiVersionBean apiVersionJsonToJson;

    @ManagedEndpoint("apiVersionJsonToJson")
    private String endpointJsonToJson;

    @Test
    public void shouldNotMakeChangesOnJsonToJsonTransformServerToClient() throws IOException {
        TestData server = jsonToTestDataObject(getJsonFromTestService(DeployedServices.JSON_DATA));
        TestData client = jsonToTestDataObject(getJsonFromGateway(endpointJsonToJson));

        assertThat(server, is(not(null)));
        assertThat(server, is(equalTo(client)));
    }

    @Test
    public void shouldNotMakeChangesOnJsonToJsonTransformClientToServer(){
        postJsonToGateway(endpointJsonToJson, getJsonFromTestService(DeployedServices.JSON_DATA));
    }
}
