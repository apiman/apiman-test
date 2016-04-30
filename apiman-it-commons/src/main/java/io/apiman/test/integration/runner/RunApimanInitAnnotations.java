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

import io.apiman.test.integration.Suite;
import io.apiman.test.integration.runner.handlers.FieldAnnotationHandler;

import java.util.List;

import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcechace
 */
public class RunApimanInitAnnotations extends Statement {

    private static final Logger LOG = LoggerFactory.getLogger(RunApimanInitAnnotations.class);

    private final Statement next;

    private final List<FieldAnnotationHandler> chain;
    private final boolean skipStatic;
    private final boolean skipInstance;

    public RunApimanInitAnnotations(Statement next, List<FieldAnnotationHandler> chain,
        boolean skipStatic, boolean skipInstance) {

        this.next = next;
        this.chain = chain;
        this.skipStatic = skipStatic;
        this.skipInstance = skipInstance;
    }

    @Override
    public void evaluate() throws Throwable {
        LOG.info("Starting apiman setup");
        for (FieldAnnotationHandler handler : chain) {
            handler.process(!skipStatic, !skipInstance);
        }
        Suite.waitForSetup();
        next.evaluate();
    }
}
