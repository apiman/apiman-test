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

package io.apiman.test.integration.ui.support.selenide.layouts;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.ByApiman;
import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.pages.administration.exportimport.ExportImportAdminPage;
import io.apiman.test.integration.ui.support.selenide.pages.administration.gateways.GatewaysAdminPage;
import io.apiman.test.integration.ui.support.selenide.pages.administration.plugins.PluginsAdminPage;
import io.apiman.test.integration.ui.support.selenide.pages.administration.policies.PolicyDefsAdminPage;
import io.apiman.test.integration.ui.support.selenide.pages.administration.roles.RolesAdminPage;

/**
 * @author jkaspar
 */
@Layout("/admin/*")
public class AdminPage<P> extends AbstractPage<P> {

    /**
     * Activates Roles tabs
     * @return RolesAdminPage
     */
    public RolesAdminPage roles() {
        $(ByApiman.i18n("a", "admin-nav.admin-roles")).click();
        return page(RolesAdminPage.class);
    }

    /**
     * Activates Policy definitions tabs
     * @return PolicyDefsAdminPage
     */
    public PolicyDefsAdminPage policies() {
        $(ByApiman.i18n("a", "admin-nav.policy-definitions")).click();
        return page(PolicyDefsAdminPage.class);
    }

    /**
     * Activates Gateways tabs
     * @return GatewaysAdminPage
     */
    public GatewaysAdminPage gateways() {
        $(ByApiman.i18n("a", "admin-nav.gateways")).click();
        return page(GatewaysAdminPage.class);
    }

    /**
     * Activates Plugins tabs
     * @return PluginsAdminPage
     */
    public PluginsAdminPage plugins() {
        $(ByApiman.i18n("a", "admin-nav.plugins")).click();
        return page(PluginsAdminPage.class);
    }

    /**
     * Activates Export/Import tabs
     * @return ExportImportAdminPage
     */
    public ExportImportAdminPage exportImport() {
        $(ByApiman.i18n("a", "admin-nav.export")).click();
        return page(ExportImportAdminPage.class);
    }
}
