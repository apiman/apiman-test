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

package io.apiman.test.integration.ui.support.beanutils;

import static io.apiman.test.integration.ui.support.beanutils.BeanUtils.uniqueName;

import io.apiman.manager.api.beans.plans.PlanBean;

/**
 * @author ldimaggi
 */
public class PlanUtils {

    public static final String TEST_PLAN_NAME_BASE = "MyTestPlan";

    /**
     * Create an instance of PlanBean with unique name and description
     * This PlanBean won't be created inside Apiman
     *
     * @return PlanBean instance
     */
    public static PlanBean local() {
        PlanBean plan = new PlanBean();
        plan.setName(uniqueName(TEST_PLAN_NAME_BASE));
        plan.setDescription(String.format("Description of %s", plan.getName()));
        return plan;
    }

}
