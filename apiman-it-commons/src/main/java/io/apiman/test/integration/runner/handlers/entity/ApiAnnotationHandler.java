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

import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.entity.APIs;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.NewApiBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */

public class ApiAnnotationHandler extends FieldAnnotationHandler<Api, NewApiBean> {

    public ApiAnnotationHandler(Object instance, Class<Api> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public NewApiBean newBeanFromAnnotation(Api annotation) {
        String name = uniqueName(annotation.name());

        NewApiBean bean = new NewApiBean();
        bean.setName(name);
        bean.setDescription(description(name));

        return bean;
    }

    private ApiBean createBean(Api annotation, Field field) {
        NewApiBean bean = newBeanFromAnnotation(annotation);
        OrganizationBean org = getStoredEntity(OrganizationBean.class, annotation.organization(), field);
        APIs client = (APIs) new APIs(org).create(bean);
        return  client.getBean();
    }

    @Override
    public void processField(Field field, Api annotation) throws IllegalAccessException {
        ApiBean bean = createBean(annotation, field);
        setField(field, ApiBean.class, bean);
    }
}
