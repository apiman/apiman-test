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

package io.apiman.test.integration.runner.restclients.entity;

import io.apiman.test.integration.runner.restclients.AbstractEntityRestClient;
import io.apiman.test.integration.runner.restclients.EntityRestClient;
import io.apiman.manager.api.beans.plugins.NewPluginBean;
import io.apiman.manager.api.beans.plugins.PluginBean;

/**
 * @author jcechace
 */
public class Plugins extends AbstractEntityRestClient<PluginBean, NewPluginBean> {

    public static final String RESOURCE_PATH = "/plugins";

    public Plugins() {
        super(RESOURCE_PATH, PluginBean.class);
    }

    @Override
    public EntityRestClient<PluginBean, NewPluginBean> create(NewPluginBean newBean) {
        PluginBean bean = post(getResourcePath(), newBean, PluginBean.class, true);
        setBean(bean);
        return this;
    }
}
