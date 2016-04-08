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

package io.apiman.test.integration.rest.apis;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.apis.UpdateApiVersionBean;

import org.junit.Before;
import org.junit.Test;

/**
 * @author opontes
 */
public class RepublishIT extends AbstractApiTest {
    public static final String FOO_URL = "http://www.fooooo:123/foo";

    @ApiVersion(api = "api", unique = true)
    private ApiVersionBean apiVersion;

    private UpdateApiVersionBean updateApiVersionBean;

    @Before
    public void setUp(){
        updateApiVersionBean = new UpdateApiVersionBean();
        updateApiVersionBean.setEndpoint(FOO_URL);
    }

    @Test
    public void canRepublishApi(){
        ApiVersions apiVersions = processApi(apiVersion);
        apiVersions.republish();

        assertThat(getStatusCode(apiVersions), is((equalTo(500))));
    }

    @Test
    public void shouldNotRepublishImplicitly(){
        ApiVersions apiVersions = processApi(apiVersion);

        assertThat(getStatusCode(apiVersions), is(not(equalTo(500))));
    }

    private int getStatusCode(ApiVersions apiVersions){
        return givenGateway().get(apiVersions.getManagedEndpoint()).getStatusCode();

    }

    private ApiVersions processApi(ApiVersionBean apiVersionBean){
        ApiVersions apiVersions = new ApiVersions(apiVersionBean.getApi());
        apiVersions.fetch(apiVersionBean.getVersion());
        return apiVersions.update(updateApiVersionBean);
    }
}
