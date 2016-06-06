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

package io.apiman.test.integration.ui.support.selenide.pages.administration.roles;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.CreateEntityForm;
import io.apiman.test.integration.ui.support.selenide.components.administration.RoleCheckboxes;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

/**
 * @author jkaspar
 */
@PageLocation("/api-manager/new-role")
public class CreateRolePage extends AbstractPage<CreateRolePage> implements
    CreateEntityForm<CreateRolePage>,
    RoleCheckboxes<CreateRolePage> {

    public RolesAdminPage create() {
        return create(RolesAdminPage.class);
    }
}
