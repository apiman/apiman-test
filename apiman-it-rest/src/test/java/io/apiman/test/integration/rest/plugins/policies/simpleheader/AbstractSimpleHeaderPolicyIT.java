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

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;

/**
 * Created by pstanko.
 */
@Plugin(artifactId = "apiman-plugins-simple-header-policy")
public abstract class AbstractSimpleHeaderPolicyIT extends AbstractApiTest {

    protected abstract String getResourceURL();

    // For my machine: https://localhost:8443/apiman-gateway/Test/echo/1.0
    protected abstract String getApiEndpoint();



}
