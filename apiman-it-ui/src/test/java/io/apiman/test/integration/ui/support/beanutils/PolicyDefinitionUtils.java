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

import io.apiman.test.integration.runner.RestAssuredConfig;
import io.apiman.test.integration.runner.restclients.entity.PolicyDefinitions;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;
import io.apiman.manager.api.beans.policies.UpdatePolicyDefinitionBean;

/**
 *
 * @author jrumanov
 */
public class PolicyDefinitionUtils {
    public static final String TEST_DEF_NAME_BASE = "MyTestDef";

    private static final String POLICY_IMPL = "class:io.apiman.gateway.engine.policies.AuthorizationPolicy";
    private static final String ICON = "users";
    private static final String DESCRIPTION = "";

    static {
        RestAssuredConfig.init();
    }

    /**
     * Create an instance of PolicyDefinitionBean with unique name and description
     * This policy definition won't be created inside Apiman
     *
     * @return PolicyDefinitionBean instance
     */
    public static PolicyDefinitionBean local() {
        PolicyDefinitionBean defBean = new PolicyDefinitionBean();
        String nameAndId = BeanUtils.uniqueName(TEST_DEF_NAME_BASE);
        defBean.setName(nameAndId);
        defBean.setId(nameAndId);
        defBean.setPolicyImpl(POLICY_IMPL);
        defBean.setIcon(ICON);
        defBean.setDescription(DESCRIPTION);
        return defBean;
    }

    /**
     * Create an instance of PolicyDefinitionBean with unique name and description
     * This  policy definition will be also create inside Apiman
     *
     * @return PolicyDefinitionBean instance
     */
    public static PolicyDefinitionBean remote() {
        PolicyDefinitionBean defBean = new PolicyDefinitionBean();
        String nameAndId = BeanUtils.uniqueName(TEST_DEF_NAME_BASE);
        defBean.setId(nameAndId);
        defBean.setPolicyImpl(POLICY_IMPL);
        defBean.setName(nameAndId);
        defBean.setIcon(ICON);
        defBean.setDescription(DESCRIPTION);

        PolicyDefinitions client = new PolicyDefinitions();
        return client.create(defBean).getBean();
    }

    /**
     * Delete Policy Definition inside Apiman
     * @param defBean policy
     */
    public static void delete(PolicyDefinitionBean defBean) {
        PolicyDefinitions client = new PolicyDefinitions();
        client.delete(defBean);
    }

    /**
     * Create an instance of UpdatePolicyDefinitionBean that has the same name, icon and description
     * as the given defBean
     * @param defBean bean that serves as template for creating the UpdatePolicyDefinitionBean
     * @return UpdatePolicyDefinitionBean instance
     */
    public static UpdatePolicyDefinitionBean updateBeanLocal(PolicyDefinitionBean defBean) {
        UpdatePolicyDefinitionBean updateBean = new UpdatePolicyDefinitionBean();
        updateBean.setName(defBean.getName());
        updateBean.setIcon(defBean.getIcon());
        updateBean.setDescription(defBean.getDescription());
        return updateBean;
    }

    /**
     *  Update the bean - setting the default values, that could be changed from the UI
     * @param policyDefId id of the bean inside the apiMan, that will be set to default
     * @param updatedBean the bean with the default values
     * @return PolicyDefinitionBean instance
     */
    public static PolicyDefinitionBean updateBeanRemote(String policyDefId, UpdatePolicyDefinitionBean updatedBean) {
        PolicyDefinitions client = new PolicyDefinitions();
        PolicyDefinitionBean defBean;

        client.update(policyDefId, updatedBean);
        defBean = client.fetch(policyDefId).getBean();
        return defBean;
    }
}
