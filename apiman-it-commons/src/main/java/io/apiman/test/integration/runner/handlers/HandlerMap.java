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

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper around Map of type HashMap<Class<?>, HandlerMap.HandlerEntityMap>
 * This class is used by ApimanRunner to store bean representations of configured Apiman resources
 *
 * @author jcechace
 */
public class HandlerMap extends HashMap<Class<?>, HandlerMap.HandlerEntityMap> {

    private static final String USUPPORTED_METHOD_MSG = "This method is not supported for %s";

    /**
     * The only purpose of this Map subclass is to make casts easier and misuse a bit harder
     *
     * @param <EntityType>
     */
    public static class HandlerEntityMap<EntityType> extends HashMap<String, EntityType> {

    }

    /**
     * Return all stored instances of given
     *
     * @param entityType type of stored instances
     * @return Map-like object with String key and EntityType value objects
     */
    @SuppressWarnings("unchecked")
    public <EntityType>  HandlerEntityMap<EntityType> getAll(Class<EntityType> entityType) {
        return super.get(entityType);
    }

    /**
     * Retrieve stored entity
     *
     * @param entityType type of the entity
     * @param key key under which the entity is stored
     * @return stored entity
     */
    public <EntityType>  EntityType get(Class<EntityType> entityType, String key) {
        HandlerEntityMap<EntityType> entitiesOfType = getAll(entityType);
        if (entitiesOfType == null) {
            return  null;
        }
        return entitiesOfType.get(key);
    }

    /**
     * Store new entity
     *
     * @param entityType type of the entity
     * @param key key used to store this entity under
     * @param entity entity to be stored
     * @return previous value or null
     */
    public <EntityType>  EntityType put(Class<EntityType> entityType, String key, EntityType entity) {
        HandlerEntityMap<EntityType> entitiesOfType = getAll(entityType);
        if (entitiesOfType == null) {
            entitiesOfType = new HandlerEntityMap<>();
            super.put(entityType, entitiesOfType);
        }
        return entitiesOfType.put(key, entity);
    }


    /*
        The following methods are not supported in order to assure correct typing.
        I might think about better solution later as this is totally against OOP principles :)
     */
    @Override
    public HandlerEntityMap get(Object key) {
        throw new UnsupportedOperationException(String.format(USUPPORTED_METHOD_MSG, getClass().getSimpleName()));
    }

    @Override
    public void putAll(Map<? extends Class<?>, ? extends HandlerEntityMap> m) {
        throw new UnsupportedOperationException(String.format(USUPPORTED_METHOD_MSG, getClass().getSimpleName()));
    }

    @Override
    public HandlerEntityMap put(Class<?> key, HandlerEntityMap value) {
        throw new UnsupportedOperationException(String.format(USUPPORTED_METHOD_MSG, getClass().getSimpleName()));
    }


}
