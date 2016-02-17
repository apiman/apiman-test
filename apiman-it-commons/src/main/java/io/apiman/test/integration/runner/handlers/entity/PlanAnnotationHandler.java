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

import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.entity.Plans;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.NewPlanBean;
import io.apiman.manager.api.beans.plans.PlanBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */

public class PlanAnnotationHandler extends FieldAnnotationHandler<Plan, NewPlanBean> {

    public PlanAnnotationHandler(Object instance, Class<Plan> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public NewPlanBean newBeanFromAnnotation(Plan annotation) {
        String name = uniqueName(annotation.name());

        NewPlanBean bean = new NewPlanBean();
        bean.setName(name);
        bean.setDescription(description(name));

        return bean;
    }

    private PlanBean createBean(Plan annotation, Field field) {
        NewPlanBean bean = newBeanFromAnnotation(annotation);
        OrganizationBean org = getStoredEntity(OrganizationBean.class, annotation.organization(), field);
        Plans client = (Plans) new Plans(org).create(bean);
        return  client.getBean();
    }

    @Override
    public void processField(Field field, Plan annotation) throws IllegalAccessException {
        PlanBean bean = createBean(annotation, field);
        setField(field, PlanBean.class, bean);
    }
}
