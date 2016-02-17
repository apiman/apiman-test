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

package io.apiman.test.integration.runner.handlers.version;

import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.clients.NewClientVersionBean;
import io.apiman.manager.api.beans.contracts.NewContractBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */
public class ClientVersionAnnotationHandler extends FieldAnnotationHandler<ClientVersion, NewClientVersionBean> {

    public ClientVersionAnnotationHandler(Object instance, Class<ClientVersion> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public NewClientVersionBean newBeanFromAnnotation(ClientVersion annotation) {
        String version = uniqueName(annotation.version(), annotation.unique());

        NewClientVersionBean bean = new NewClientVersionBean();
        bean.setVersion(version);
        if (!annotation.cloneOf().isEmpty()) {
            bean.setClone(true);
            bean.setCloneVersion(annotation.cloneOf());
        }
        return bean;
    }

    private ClientVersions createBean(ClientVersion annotation, Field field) {
        NewClientVersionBean bean = newBeanFromAnnotation(annotation);
        ClientBean app = getStoredEntity(ClientBean.class, annotation.client(), field);
        ClientVersions client = (ClientVersions) new ClientVersions(app).create(bean);
        return  client;
    }

    private void lockBean(ClientVersion annotation, ClientVersions client) {
        if (annotation.publish()) {
            client.lock();
        }
    }

    private void configureContracts(ClientVersion annotation, ClientVersions client) {
        for (Contract contract : annotation.contracts()) {
            PlanVersionBean planVersion = getStoredEntity(PlanVersionBean.class, contract.vPlan(), null);
            ApiVersionBean apiVersion = getStoredEntity(ApiVersionBean.class, contract.vApi(), null);

            NewContractBean bean = new NewContractBean();
            bean.setPlanId(planVersion.getPlan().getId());
            bean.setApiOrgId(apiVersion.getApi().getOrganization().getId());
            bean.setApiId(apiVersion.getApi().getId());
            bean.setApiVersion(apiVersion.getVersion());

            client.contracts().add(bean);
        }
    }

    @Override
    public void processField(Field field, ClientVersion annotation) throws ReflectiveOperationException {
        ClientVersions client = createBean(annotation, field);
        PolicyConfigurationUtils.configurePolicies(annotation.policies(), client);
        configureContracts(annotation, client);
        lockBean(annotation, client);

        ClientVersionBean bean = client.getBean();
        setField(field, ClientVersionBean.class, bean);
    }
}
