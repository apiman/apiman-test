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
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * Base class used for annotation processing. Provides functionality to retrieve annotated fields
 * and set their value.
 *
 * @author jcechace
 */
public abstract class AbstractAnnotationHandler<HandledAnnotation extends Annotation, NewBean> {

    private final Class<?> clazz;
    private final Class<HandledAnnotation> annotationClass;

    public AbstractAnnotationHandler(Class<?> clazz, Class<HandledAnnotation> annotationClass) {
        this.clazz = clazz;
        this.annotationClass = annotationClass;
    }

    public Class<HandledAnnotation> getAnnotationClass() {
        return annotationClass;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    protected static String description(String name) {
        return String.format("Description of %s", name);
    }


    /**
     * Create semi-unique name by appending current system time millis
     *
     * @param name base name
     * @return unique name
     */
    protected String uniqueName(String name) {
        return uniqueName(name, true);
    }

    /**
     * Create semi-unique name by appending current system time millis if requested
     *
     * @param name base name
     * @return unique name
     */
    protected String uniqueName(String name, boolean unique) {
        if (!unique) {
            return name;
        }
        return name + System.currentTimeMillis() + (int) (Math.random() * 10000 + 1);
    }

    /**
     * If possible assign value to given field
     *
     * @param field filed to be assigned to
     * @param value assigned value
     * @throws IllegalAccessException
     */
    public <ValueType> void setField(Object target, Field field, Class<ValueType> valueType, ValueType value)
            throws IllegalAccessException {
        if (field.getType().isAssignableFrom(value.getClass())) {
            field.setAccessible(true);
            field.set(target, value);
        }
    }


    /**
     * Get all fields of target instance annotated by given annotation type
     *
     * @param annotationClass type of annotation
     * @return annotated fields
     */
    public List<Field> annotatedFields(Class<? extends Annotation> annotationClass) {
        return FieldUtils.getFieldsListWithAnnotation(clazz, annotationClass);
    }

    /**
     * Get all fields annotated by HandledAnnotation
     *
     * @return annotated fields
     */
    public List<Field> annotatedFields() {
        return annotatedFields(annotationClass);
    }


    /**
     * Creates entity bean used for POST request
     *
     * @param annotation annotation instance
     * @return entity of type NewBean used for POST request
     */
    public abstract NewBean newBeanFromAnnotation(HandledAnnotation annotation);

    /**
     * Process annotation type
     */
    public abstract void process();
}
