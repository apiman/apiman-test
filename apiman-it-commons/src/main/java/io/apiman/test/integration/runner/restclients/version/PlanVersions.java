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
import io.apiman.test.integration.runner.restclients.entity.Policies;
import io.apiman.manager.api.beans.actions.ActionBean;
import io.apiman.manager.api.beans.actions.ActionType;
import io.apiman.manager.api.beans.plans.NewPlanVersionBean;
import io.apiman.manager.api.beans.plans.PlanBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

/**
 * @author jcechace
 */
public class PlanVersions extends AbstractEntityRestClient<PlanVersionBean, NewPlanVersionBean>
    implements VersionRestClient<PlanVersions> {

    public static final String RESOURCE_PATH = "/organizations/{0}/plans/{1}/versions";

    public PlanVersions(PlanBean plan) {
        super(path(RESOURCE_PATH, plan.getOrganization().getId(), plan.getId()), PlanVersionBean.class);
    }

    @Override
    public Policies<PlanVersions> policies() {
        return new Policies<>(this, getResourcePath(getBean().getVersion()));
    }

    @Override
    public PlanVersions lock() {
        ActionBean action = new ActionBean();
        action.setType(ActionType.lockPlan);
        action.setOrganizationId(getBean().getPlan().getOrganization().getId());
        action.setEntityId(getBean().getPlan().getId());
        action.setEntityVersion(getBean().getVersion());

        post(path(ACTION_PATH), action);
        return this;
    }
}
