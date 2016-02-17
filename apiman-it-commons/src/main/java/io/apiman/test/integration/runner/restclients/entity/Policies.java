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

import io.apiman.test.integration.runner.restclients.AbstractRestWrapper;
import io.apiman.manager.api.beans.policies.NewPolicyBean;
import io.apiman.manager.api.beans.policies.PolicyBean;
import io.apiman.manager.api.beans.summary.PolicySummaryBean;

import java.util.Arrays;
import java.util.List;

/**
 * @author jcechace
 */
public class Policies<EntityClientType> extends AbstractRestWrapper {

    public static final String RESOURCE_PATH = "/policies";

    private final EntityClientType entityClient;
    private final String entityPath;

    public Policies(EntityClientType entityClient, String entityPath) {
        this.entityClient = entityClient;
        this.entityPath = entityPath;
    }

    public Policies<EntityClientType> add(NewPolicyBean bean) {
        post(path(entityPath + RESOURCE_PATH), bean, PolicyBean.class);
        return this;
    }

    public List<PolicySummaryBean> fetchAll() {
        PolicySummaryBean[] policies = get(path(entityPath + RESOURCE_PATH), PolicySummaryBean[].class);
        return Arrays.asList(policies);
    }

    public boolean containsType(String policyDefId) {
        for (PolicySummaryBean summary : fetchAll()) {
            String currentDefId = summary.getPolicyDefinitionId();
            if (currentDefId.equals(policyDefId)) {
                return true;
            }
        }
        return false;
    }

    public EntityClientType parentEntity() {
        return entityClient;
    }
}
