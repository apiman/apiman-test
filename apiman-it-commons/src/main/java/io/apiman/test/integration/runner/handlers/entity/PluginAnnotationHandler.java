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

import io.apiman.test.integration.SuiteProperties;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.runner.handlers.ClassAnnotationHandler;
import io.apiman.test.integration.runner.restclients.entity.Plugins;
import io.apiman.manager.api.beans.plugins.NewPluginBean;

/**
 * @author jcechace
 */
public class PluginAnnotationHandler extends ClassAnnotationHandler<Plugin, NewPluginBean> {

    public PluginAnnotationHandler(Class<?> clazz, Class<Plugin> pluginClass) {
        super(clazz, pluginClass);
    }

    @Override
    public NewPluginBean newBeanFromAnnotation(Plugin annotation) {
        String pluginVersion;
        if (annotation.version().isEmpty()) {
            pluginVersion = SuiteProperties.getProperty(SuiteProperties.APIMAN_PLUGIN_VERSION_PROP);
        } else {
            pluginVersion = annotation.version();
        }

        NewPluginBean bean = new NewPluginBean();
        bean.setGroupId(annotation.groupId());
        bean.setArtifactId(annotation.artifactId());
        bean.setVersion(pluginVersion);
        bean.setName(uniqueName(annotation.name()));
        bean.setType(annotation.type());
        bean.setDescription(annotation.description());

        return bean;
    }


    @Override
    public void processClass(Plugin annotation) {
        NewPluginBean bean = newBeanFromAnnotation(annotation);
        new Plugins().create(bean);
    }
}
