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

package io.apiman.test.integration.rest.plugins.policies.simpleheader;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenGateway;
import static io.apiman.test.integration.runner.RestAssuredUtils.when;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;

import com.jayway.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by pstanko.
 */
@Plugin(artifactId = "apiman-plugins-simple-header-policy")
public abstract class AbstractSimpleRequestHeaderPolicyIT extends AbstractApiTest {

    protected static final String HEADER_NAME = "X-Request";
    protected static final String HEADER_VALUE = "This is request";

    protected abstract String getResourceURL();

    protected abstract String getApiEndpoint();

    @Test
    public void shouldAddXRequestHeaderAtRequest() throws Exception {
        when().
            get(getResourceURL()).
        then().
            body("headers." + HEADER_NAME, equalTo(HEADER_VALUE));
    }

    @Test
    public void shouldNotAddXRequestHeaderAtRequest() throws Exception {
        when().
            get(getResourceURL()).
        then().
            header(HEADER_NAME, isEmptyOrNullString());

    }

}
