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

package io.apiman.test.integration.ui.support.selenide.pages.plans;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.CreateChildEntityForm;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;
import io.apiman.test.integration.ui.support.selenide.pages.plans.detail.PlanDetailPage;

/**
 * @author ldimaggi
 */
@PageLocation("/api-manager/new-plan")
public class CreatePlanPage extends AbstractPage<CreatePlanPage> implements
    CreateChildEntityForm<CreatePlanPage> {

    /**
     * Click the create button
     * @return PlanDetailPage page object
     */
    public PlanDetailPage create() {
        return create(PlanDetailPage.class);
    }
}
