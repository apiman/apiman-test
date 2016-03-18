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

package io.apiman.test.integration.ui.tests.plans;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static org.hamcrest.CoreMatchers.is;

import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.version.PlanVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractPlanTest;
import io.apiman.test.integration.ui.support.selenide.pages.plans.CreatePlanVersionPage;
import io.apiman.manager.api.beans.plans.PlanVersionBean;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jrumanov
 */
public class ClonePlanVersionIT extends AbstractPlanTest {

    private static final String CLONED_NAME = "2.0";

    private static PlanVersions planVersions;

    @PlanVersion(plan = "plan", policies = @Policies("arbitrary"))
    private static PlanVersionBean version;

    @BeforeClass
    public static void setUp() {
        planVersions = new PlanVersions(plan);
    }

    @Test
    public void canClonePlanVersion() throws Exception {
        CreatePlanVersionPage createVersionPage =
            open(CreatePlanVersionPage.class, organization.getId(), plan.getId(), version.getVersion());

        createVersionPage
            .version(CLONED_NAME)
            .clone(true)
            .create();

        planVersions.peek(CLONED_NAME);
        planVersions.fetch(CLONED_NAME);
        Assert.assertThat(planVersions.policies().fetchAll().size(), is(1));
    }
}
