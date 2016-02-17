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

package io.apiman.test.integration.runner.handlers.misc;

import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;
import io.apiman.manager.api.beans.summary.ContractSummaryBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */
public class ApiKeyAnnotationHandler extends FieldAnnotationHandler<ApiKey, Void> {

    public ApiKeyAnnotationHandler(Object instance, Class<ApiKey> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public void processField(Field field, ApiKey annotation) throws ReflectiveOperationException {
        ClientVersionBean vClient = getStoredEntity(ClientVersionBean.class, annotation.vClient(), field);
        ClientVersions client =
            (ClientVersions) new ClientVersions(vClient.getClient()).fetch(vClient.getVersion());

        ApiVersionBean vApi = getStoredEntity(ApiVersionBean.class, annotation.vApi(), field);
        PlanVersionBean vPlan = getStoredEntity(PlanVersionBean.class, annotation.vPlan(), field);
        OrganizationBean org = vApi.getApi().getOrganization();

        ContractSummaryBean contract = client.contracts().fetch(org, vApi, vPlan).getBean();
        setField(field, String.class, contract.getApikey());
    }

    @Override
    public Void newBeanFromAnnotation(ApiKey annotation) {
        throw new UnsupportedOperationException("Resource does not support POST requests");
    }
}
