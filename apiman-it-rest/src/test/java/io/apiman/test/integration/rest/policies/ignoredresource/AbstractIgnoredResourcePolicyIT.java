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

package io.apiman.test.integration.rest.policies.ignoredresource;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.categories.PolicyTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jcechace
 */
@Category({PolicyTest.class})
public abstract class AbstractIgnoredResourcePolicyIT extends AbstractApiTest {

    protected static final String IGNORE_SIMPLE = "/path/to/ignore";

    protected static final String PATH_IGNORED_SIMPLE = IGNORE_SIMPLE;
    protected static final String PATH_IGNORED_REGEXP = "/path/foobar";
    protected static final String PATH_AVAILABLE = "/path/available";

    protected abstract String getResourceURL(String path);

    @Test
    public void shouldFailWhenGetRequestToIgnoredPath() {
        getRequest(getResourceURL(PATH_IGNORED_SIMPLE), 404);
    }

    @Test
    public void shouldFailWhenPostRequestToIgnoredPath() {
        postRequest(getResourceURL(PATH_IGNORED_SIMPLE), 404);
    }


    @Test
    public void shouldFailWhenGetRequestToRegexIgnoredPath() {
        getRequest(getResourceURL(PATH_IGNORED_REGEXP), 404);
    }

    @Test
    public void shouldFailWhenPostRequestToRegexIgnoredPath() {
        postRequest(getResourceURL(PATH_IGNORED_REGEXP), 404);
    }

    @Test
    public void shouldPassWhenGetRequestToAvailablePath() {
        getRequest(getResourceURL(PATH_AVAILABLE), 200);
    }

    @Test
    public void shouldPassWhenPostRequestToAvailablePath() {
        postRequest(getResourceURL(PATH_AVAILABLE), 200);
    }
}
