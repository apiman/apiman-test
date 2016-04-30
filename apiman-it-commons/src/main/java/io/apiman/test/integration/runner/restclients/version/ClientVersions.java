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

package io.apiman.test.integration.runner.restclients.version;

import io.apiman.test.integration.runner.restclients.AbstractEntityRestClient;
import io.apiman.test.integration.runner.restclients.VersionRestClient;
import io.apiman.test.integration.runner.restclients.entity.Contracts;
import io.apiman.test.integration.runner.restclients.entity.Policies;
import io.apiman.manager.api.beans.actions.ActionBean;
import io.apiman.manager.api.beans.actions.ActionType;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.clients.NewClientVersionBean;
import io.apiman.manager.api.beans.metrics.ClientUsagePerApiBean;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author jcechace
 */
public class ClientVersions extends AbstractEntityRestClient<ClientVersionBean, NewClientVersionBean>
    implements VersionRestClient<ClientVersions> {

    public static final String RESOURCE_PATH = "/organizations/{0}/clients/{1}/versions";
    public static final String METRICS_API_USAGE = "/metrics/apiUsage";

    public ClientVersions(ClientBean app) {
        super(path(RESOURCE_PATH, app.getOrganization().getId(), app.getId()), ClientVersionBean.class);
    }

    @Override
    public Policies<ClientVersions> policies() {
        return new Policies<>(this, getResourcePath(getBean().getVersion()));
    }

    public Contracts contracts() {
        return new Contracts(this, getResourcePath(getBean().getVersion()));
    }

    @Override
    public ClientVersions publish() {
        ActionBean action = new ActionBean();
        action.setType(ActionType.registerClient);
        action.setOrganizationId(getBean().getClient().getOrganization().getId());
        action.setEntityId(getBean().getClient().getId());
        action.setEntityVersion(getBean().getVersion());

        post(path(ACTION_PATH), action);
        return this;
    }

    public ClientUsagePerApiBean metrics(LocalDateTime from, LocalDateTime to) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_API_USAGE, ClientUsagePerApiBean.class);
    }
}
