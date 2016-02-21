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

package io.apiman.test.integration.ui.support.beanutils;

import static io.apiman.test.integration.ui.support.beanutils.BeanUtils.uniqueName;

import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

/**
 * @author jcechace
 */
public class ApiUtils {

    public static final String TEST_API_NAME_BASE = "MyTestApi";

    /**
     * Create an instance of ApiBean with unique name and description
     * This organization won't be created inside Apiman
     *
     * @return ApiBean instance
     */
    public static ApiBean local(OrganizationBean orgBean) {
        ApiBean api = new ApiBean();
        api.setOrganization(orgBean);
        api.setName(uniqueName(TEST_API_NAME_BASE));
        api.setDescription(String.format("Description of %s", api.getName()));
        return api;
    }
}
