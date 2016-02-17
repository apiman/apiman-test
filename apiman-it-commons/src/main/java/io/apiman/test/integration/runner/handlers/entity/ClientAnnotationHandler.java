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

package io.apiman.test.integration.runner.handlers.entity;

import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.entity.Clients;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.clients.NewClientBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */
public class ClientAnnotationHandler extends FieldAnnotationHandler<Client, NewClientBean> {

    public ClientAnnotationHandler(Object instance, Class<Client> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public NewClientBean newBeanFromAnnotation(Client annotation) {
        String name = uniqueName(annotation.name());

        NewClientBean bean = new NewClientBean();
        bean.setName(name);
        bean.setDescription(description(name));

        return bean;
    }

    private ClientBean createBean(Client annotation, Field field) {
        NewClientBean bean = newBeanFromAnnotation(annotation);
        OrganizationBean org = getStoredEntity(OrganizationBean.class, annotation.organization(), field);
        Clients client = (Clients) new Clients(org).create(bean);
        return  client.getBean();
    }

    @Override
    public void processField(Field field, Client annotation) throws IllegalAccessException {
        ClientBean bean = createBean(annotation, field);
        setField(field, ClientBean.class, bean);
    }
}
