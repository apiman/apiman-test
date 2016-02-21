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

package io.apiman.test.integration.runner.restclients.entity;

import static io.apiman.test.integration.runner.RestAssuredUtils.withManager;

import io.apiman.test.integration.runner.restclients.AbstractEntityRestClient;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;
import io.apiman.manager.api.beans.policies.UpdatePolicyDefinitionBean;

import com.jayway.restassured.response.Response;

/**
 *
 * @author jrumanov
 */
public class PolicyDefinitions extends AbstractEntityRestClient<PolicyDefinitionBean, PolicyDefinitionBean> {

    public static final String RESOURCE_PATH = "/policyDefs";

    public PolicyDefinitions() {
        super(RESOURCE_PATH, PolicyDefinitionBean.class);
    }

    public PolicyDefinitions update(String id, UpdatePolicyDefinitionBean updateBean) {
        put(getResourcePath(id), updateBean);
        return this;
    }

    public Response delete(PolicyDefinitionBean bean) {
        return withManager().delete(getResourcePath(bean.getId()));
    }
}
