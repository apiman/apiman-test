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

import io.apiman.test.integration.Suite;
import io.apiman.test.integration.runner.annotations.misc.Endpoint;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.restclients.version.ApiVersions;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiPlanBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.apis.NewApiVersionBean;
import io.apiman.manager.api.beans.apis.UpdateApiVersionBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jcechace
 */
public class ApiVersionAnnotationHandler extends FieldAnnotationHandler<ApiVersion, NewApiVersionBean> {

    public static final String API_HOST = System.getProperty("test_serverUrl", "localhost");
    public static final String API_PORT = System.getProperty("test_consolePort", "8080");

    public ApiVersionAnnotationHandler(Object instance, Class<ApiVersion> annotationClass, HandlerMap handlerMap) {
        super(instance, annotationClass, handlerMap);
    }

    @Override
    public NewApiVersionBean newBeanFromAnnotation(ApiVersion annotation) {
        String version = uniqueName(annotation.version(), annotation.unique());

        NewApiVersionBean bean = new NewApiVersionBean();
        bean.setVersion(version);
        if (!annotation.cloneOf().isEmpty()) {
            bean.setClone(true);
            bean.setCloneVersion(annotation.cloneOf());
        }
        return bean;
    }

    private ApiVersions createBean(ApiVersion annotation, Field field) {
        NewApiVersionBean bean = newBeanFromAnnotation(annotation);
        ApiBean api = getStoredEntity(ApiBean.class, annotation.api(), field);
        ApiVersions client = (ApiVersions) new ApiVersions(api).create(bean);
        return  client;
    }

    private void configureEndpoint(ApiVersion annotation, Field field, ApiVersions client) {
        Endpoint endpoint = annotation.endpoint();
        String value = endpoint.value();
        String path = value.startsWith("/") ? value.substring(1) : value;
        String url = Suite.getDeploymentUrl() + "/" + path;

        UpdateApiVersionBean updateBean = new UpdateApiVersionBean();
        updateBean.setEndpoint(url);
        updateBean.setEndpointType(endpoint.type());
        updateBean.setEndpointContentType(endpoint.contentType());
        updateBean.setPublicAPI(annotation.isPublic());
        client.update(updateBean);
    }

    private void configurePlans(ApiVersion annotation, Field field, ApiVersions client) {
        Set<ApiPlanBean> plans = new HashSet<>();
        for (String planFieldName : annotation.vPlans()) {
            PlanVersionBean planVersion = getStoredEntity(PlanVersionBean.class, planFieldName, field);

            ApiPlanBean bean = new ApiPlanBean();
            bean.setPlanId(planVersion.getPlan().getId());
            bean.setVersion(planVersion.getVersion());

            plans.add(bean);
        }
        if (!plans.isEmpty()) {
            UpdateApiVersionBean updateBean = new UpdateApiVersionBean();
            updateBean.setPlans(plans);
            client.update(updateBean);
        }
    }

    private void lockBean(ApiVersion annotation, ApiVersions client) {
        if (annotation.publish()) {
            client.publish();
        }
    }

    @Override
    public void processField(Field field, ApiVersion annotation) throws ReflectiveOperationException {
        ApiVersions client = createBean(annotation, field);
        configureEndpoint(annotation, field, client);
        configurePlans(annotation, field, client);
        configurePolicies(annotation.policies(), client);
        lockBean(annotation, client);

        ApiVersionBean bean = client.getBean();
        setField(field, ApiVersionBean.class, bean);
    }
}
