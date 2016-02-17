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

package io.apiman.test.integration.runner;

import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.handlers.ClassAnnotationHandler;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;
import io.apiman.test.integration.runner.handlers.HandlerMap;
import io.apiman.test.integration.runner.handlers.entity.ApiAnnotationHandler;
import io.apiman.test.integration.runner.handlers.entity.ClientAnnotationHandler;
import io.apiman.test.integration.runner.handlers.entity.OrganizationAnnotationHandler;
import io.apiman.test.integration.runner.handlers.entity.PlanAnnotationHandler;
import io.apiman.test.integration.runner.handlers.entity.PluginAnnotationHandler;
import io.apiman.test.integration.runner.handlers.misc.ApiKeyAnnotationHandler;
import io.apiman.test.integration.runner.handlers.misc.ManagedEndpointAnnotationHandler;
import io.apiman.test.integration.runner.handlers.version.ApiVersionAnnotationHandler;
import io.apiman.test.integration.runner.handlers.version.ClientVersionAnnotationHandler;
import io.apiman.test.integration.runner.handlers.version.PlanVersionAnnotationHandler;

import java.util.ArrayList;
import java.util.List;

import org.junit.internal.runners.statements.Fail;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Custom JUnit runner used to run Apiman integration test.
 * The runner allows the configuration of running Apiman instance with annotations
 * and injection beans for created resources.
 *
 * @author jcechace
 *
 * All resources are created and processed in following order:
 *  1) Organization
 *  2) Plan
 *  3) Api
 *  4) Client
 */
public class ApimanRunner extends BlockJUnit4ClassRunner {

    private final HandlerMap handlerMap = new HandlerMap();

    public ApimanRunner(Class<?> klass) throws Exception {
        super(klass);
        processClassAnnotations(getTestClass().getJavaClass());
    }

    @Override
    protected Statement withBeforeClasses(Statement statement) {
        Object target;
        try {
            target = createTest();
        } catch (Exception e) {
            return new Fail(e);
        }

        List<FieldAnnotationHandler> chain = annotationFieldProcessChain(target);
        statement = super.withBeforeClasses(statement);
        return new RunApimanInitAnnotations(statement, chain, false, true);
    }

    @Override
    protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
        List<FieldAnnotationHandler> chain = annotationFieldProcessChain(target);
        statement = super.withBefores(method, target, statement);
        return new RunApimanInitAnnotations(statement, chain, true, false);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return super.methodInvoker(method, test);
    }

    /**
     * Specifies the processing order of class level annotations
     * @return list of annotation handlers to be used for annotation processing
     */
    protected List<ClassAnnotationHandler> annotationClassProcessChain(Class<?> klass) {
        List<ClassAnnotationHandler> chain = new ArrayList<>();
        chain.add(new PluginAnnotationHandler(klass, Plugin.class));
        return chain;
    }

    /**
     * Specifies the processing order of instance level annotations
     * @return list of annotation handlers to be used for annotation processing
     */
    protected List<FieldAnnotationHandler> annotationFieldProcessChain(Object test) {
        List<FieldAnnotationHandler> chain = new ArrayList<>();
        // organization processing
        chain.add(new OrganizationAnnotationHandler(test, Organization.class, handlerMap));
        // plan processing
        chain.add(new PlanAnnotationHandler(test, Plan.class, handlerMap));
        chain.add(new PlanVersionAnnotationHandler(test, PlanVersion.class, handlerMap));
        // api processing
        chain.add(new ApiAnnotationHandler(test, Api.class, handlerMap));
        chain.add(new ApiVersionAnnotationHandler(test, ApiVersion.class, handlerMap));
        chain.add(new ManagedEndpointAnnotationHandler(test, ManagedEndpoint.class, handlerMap));
        // clientApp processing
        chain.add(new ClientAnnotationHandler(test, Client.class, handlerMap));
        chain.add(new ClientVersionAnnotationHandler(test, ClientVersion.class, handlerMap));
        chain.add(new ApiKeyAnnotationHandler(test, ApiKey.class, handlerMap));
        return chain;
    }

    protected void processClassAnnotations(Class<?> klass) {
        for (ClassAnnotationHandler handler : annotationClassProcessChain(klass)) {
            handler.process();
        }
    }

}
