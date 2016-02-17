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

import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.entity.Organizations;
import io.apiman.manager.api.beans.orgs.NewOrganizationBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */
public class OrganizationAnnotationHandler extends FieldAnnotationHandler<Organization, NewOrganizationBean> {


    public OrganizationAnnotationHandler(Object instance, Class<Organization> organizationClass, HandlerMap handlerMap) {
        super(instance, organizationClass, handlerMap);
    }

    @Override
    public NewOrganizationBean newBeanFromAnnotation(Organization annotation) {
        String name = uniqueName(annotation.name());

        NewOrganizationBean bean = new NewOrganizationBean();
        bean.setName(name);
        bean.setDescription(description(name));

        return bean;
    }

    private OrganizationBean createBean(Organization annotation) {
        NewOrganizationBean bean = newBeanFromAnnotation(annotation);
        Organizations client = (Organizations) new Organizations().create(bean);
        return  client.getBean();
    }

    @Override
    public void processField(Field field, Organization annotation) throws IllegalAccessException {
        OrganizationBean bean = createBean(annotation);
        setField(field, OrganizationBean.class, bean);
    }
}
