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

package io.apiman.test.integration.runner.handlers;

import java.lang.annotation.Annotation;

/**
 * Base Class for processing class level annotations
 *
 * @author jcechace
 */
public abstract class ClassAnnotationHandler<HandledAnnotation extends Annotation, NewBean>
        extends AbstractAnnotationHandler<HandledAnnotation, NewBean> {

    public ClassAnnotationHandler(Class<?> clazz, Class<HandledAnnotation> handledAnnotationClass) {
        super(clazz, handledAnnotationClass);
    }


    @Override
    public void process() {
        HandledAnnotation annotation = getClazz().getAnnotation(getAnnotationClass());
        if (annotation != null) {
            processClass(annotation);
        }
    }

    /**
     * Process class level HandledAnnotation instance
     *
     * @param annotation annotation instance;
     */
    public abstract void processClass(HandledAnnotation annotation);

}
