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

import static io.apiman.test.integration.runner.handlers.version.PolicyConfigurationUtils.configurePolicies;

import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.version.PlanVersions;
import io.apiman.manager.api.beans.plans.NewPlanVersionBean;
import io.apiman.manager.api.beans.plans.PlanBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */
public class PlanVersionAnnotationHandler extends FieldAnnotationHandler<PlanVersion, NewPlanVersionBean> {

    public PlanVersionAnnotationHandler(Object instance, Class<PlanVersion> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public NewPlanVersionBean newBeanFromAnnotation(PlanVersion annotation) {
        String version = uniqueName(annotation.version(), annotation.unique());

        NewPlanVersionBean bean = new NewPlanVersionBean();
        bean.setVersion(version);
        if (!annotation.cloneOf().isEmpty()) {
            bean.setClone(true);
            bean.setCloneVersion(annotation.cloneOf());
        }
        return bean;
    }

    private PlanVersions createBean(PlanVersion annotation, Field field) {
        NewPlanVersionBean bean = newBeanFromAnnotation(annotation);
        PlanBean plan = getStoredEntity(PlanBean.class, annotation.plan(), field);
        PlanVersions client = (PlanVersions) new PlanVersions(plan).create(bean);
        return  client;
    }

    private void lockBean(PlanVersion annotation, PlanVersions client) {
        if (annotation.publish()) {
            client.lock();
        }
    }

    @Override
    public void processField(Field field, PlanVersion annotation) throws ReflectiveOperationException {
        PlanVersions client = createBean(annotation, field);
        configurePolicies(annotation.policies(), client);
        lockBean(annotation, client);

        PlanVersionBean bean = client.getBean();
        setField(field, PlanVersionBean.class, bean);
    }
}
