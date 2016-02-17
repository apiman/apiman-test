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
import java.lang.reflect.Modifier;

/**
 * Base Class for processing field level annotations
 * @author jcechace
 */
public abstract class FieldAnnotationHandler<HandledAnnotation extends Annotation, NewBean>
        extends AbstractAnnotationHandler<HandledAnnotation, NewBean> {

    private final Object instance;
    private final HandlerMap handlerMap;

    /**
     * Create new filed annotation handler
     *
     * @param instance object instance to be processed
     * @param annotationClass annotation type to be processed
     * @param handlerMap handler map instance used to store entities
     */
    public FieldAnnotationHandler(Object instance, Class<HandledAnnotation> annotationClass, HandlerMap handlerMap) {
        super(instance.getClass(), annotationClass);
        this.instance = instance;
        this.handlerMap = handlerMap;
    }

    public Object getInstance() {
        return instance;
    }

    public HandlerMap getHandlerMap() {
        return handlerMap;
    }


    /**
     * Retrieves an entity of given type stored under specified key.
     *
     * @param entityType type of stored entity
     * @param key key used to store the entity, if null field name will be used
     * @param field current field name
     * @return stored entity
     * @throws IllegalArgumentException if both key and filed are null
     */
    protected  <EntityType> EntityType getStoredEntity(Class<EntityType> entityType, String key, Field field) {
        if (key == null || key.isEmpty()) {
            if (field == null) {
                throw new IllegalArgumentException("Either valid key or instance field is required");
            }
            key = field.getName();
        }
        return getHandlerMap().get(entityType, key);
    }

    /**
     * Stores an entity produced during annotation processing.
     * The type of stored entity and field name will be used as keys.
     *
     * @param entityType type of given entity object
     * @param field currently processed field
     * @param entity entity object
     * @return previously stored entity
     */
    protected  <EntityType> EntityType storeEntity(Class<EntityType> entityType, Field field, EntityType entity) {
        return getHandlerMap().put(entityType, field.getName(), entity);
    }

    /**
     * Helper used to determine whether given field is static
     *
     * @param field filed
     * @return true if static
     */
    protected boolean isStaticField(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * Sets the value of filed on given object
     *
     * @param field field to be populated
     * @param valueType value type
     * @param value value
     * @throws IllegalAccessException
     */
    public void setField(Field field, Class valueType, Object value) throws IllegalAccessException {
        super.setField(instance, field, valueType, value);
        storeEntity(valueType, field, value);
    }


    /**
     * process annotation
     *
     * @param staticMembers whether static fields should be processed
     * @param instanceMembers whether instance fields should be processed
     */
    public void process(boolean staticMembers, boolean instanceMembers) {
        for (Field field : annotatedFields()) {
            try {
                if ((isStaticField(field) && staticMembers) || (!isStaticField(field) && instanceMembers)) {
                    processField(field, field.getAnnotation(getAnnotationClass()));
                }
            } catch (ReflectiveOperationException e) {
                throw new AnnotationHandlerException(String.format("Unable to process filed %s", field.getName()), e);
            }
        }
    }

    /**
     * Process annotation on all instance fields
     */
    @Override
    public void process() {
        process(false, true);
    }

    /**
     * Process field annotated by HandledAnnotation
     *
     * @param field field to be processed
     * @param annotation annotation to be processed
     * @throws ReflectiveOperationException
     */
    public abstract void processField(Field field, HandledAnnotation annotation) throws ReflectiveOperationException;

}
