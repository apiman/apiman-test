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
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.contracts.ContractBean;
import io.apiman.manager.api.beans.contracts.NewContractBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.PlanBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;
import io.apiman.manager.api.beans.summary.ContractSummaryBean;

import java.util.Arrays;
import java.util.List;

/**
 * @author jcechace
 */
public class Contracts extends AbstractRestWrapper {

    public static final String CREATE_PATH = "/contracts";

    private final ClientVersions entityClient;
    private final String entityPath;

    private ContractSummaryBean bean;

    public Contracts(ClientVersions entityClient, String entityPath) {
        this.entityClient = entityClient;
        this.entityPath = entityPath;
    }

    public ContractSummaryBean getBean() {
        return bean;
    }

    public Contracts add(NewContractBean bean) {
        post(path(entityPath + CREATE_PATH), bean, ContractBean.class);
        return this;
    }

    public List<ContractSummaryBean> fetchAll() {
        return Arrays.asList(get(path(entityPath + CREATE_PATH), ContractSummaryBean[].class));
    }

    public Contracts fetch(OrganizationBean org, ApiVersionBean vApi, PlanVersionBean vPlan) {
        for (ContractSummaryBean csb : fetchAll()) {
            boolean apiMatch = apiMatch(vApi, csb.getApiId(), csb.getApiVersion());
            boolean planMatch = planMatch(vPlan, csb.getPlanId(), csb.getPlanVersion());
            boolean organisationMath = organisationMatch(org, csb.getApiOrganizationId());

            if (organisationMath && apiMatch && planMatch) {
                this.bean = csb;
                return this;
            }
        }
        return this;
    }

    private boolean apiMatch(ApiVersionBean vApi, String id, String version) {
        ApiBean api = vApi.getApi();
        return api.getId().equals(id) && vApi.getVersion().equals(version);
    }

    private boolean planMatch(PlanVersionBean vPlan, String id, String version) {
        PlanBean plan = vPlan.getPlan();
        return plan.getId().equals(id) && vPlan.getVersion().equals(version);
    }

    private boolean organisationMatch(OrganizationBean org, String id) {
        return org.getId().equals(id);
    }

    public ClientVersions parentEntity() {
        return entityClient;
    }
}
