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

import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.manager.api.beans.apis.ApiVersionBean;

import java.lang.reflect.Field;

/**
 * @author jcechace
 */
public class ManagedEndpointAnnotationHandler extends FieldAnnotationHandler<ManagedEndpoint, Void> {

    public ManagedEndpointAnnotationHandler(Object instance, Class<ManagedEndpoint> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public Void newBeanFromAnnotation(ManagedEndpoint annotation) {
        throw new UnsupportedOperationException("Resource does not support POST requests");
    }

    @Override
    public void processField(Field field, ManagedEndpoint annotation) throws IllegalAccessException {
        ApiVersionBean bean = getStoredEntity(ApiVersionBean.class, annotation.value(), field);
        ApiVersions client =
            (ApiVersions) new ApiVersions(bean.getApi()).fetch(bean.getVersion());
        String managedEndpoint = client.getManagedEndpoint();
        setField(field, String.class, managedEndpoint);
    }
}
