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

package io.apiman.test.integration.ui.support.selenide.pages.plans.detail;

import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.PageLocation;

/**
 * Created by Jarek Kaspar, ldimaggi
 */
@Layout
@PageLocation("/api-manager/orgs/{0}/plans/{1}/{2}")
public class PlanVersionDetailPage extends AbstractPlanDetailPage<PlanVersionDetailPage> {
}
