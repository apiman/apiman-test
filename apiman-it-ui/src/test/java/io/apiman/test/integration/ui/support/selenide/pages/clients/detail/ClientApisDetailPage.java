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

package io.apiman.test.integration.ui.support.selenide.pages.clients.detail;

import static com.codeborne.selenide.Condition.hasAttribute;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.ByApiman;
import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.components.ModalDialog;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * @author ldimaggi, jcechace
 */
@PageLocation("/api-manager/orgs/{0}/clients/{1}/{2}/apis")
public class ClientApisDetailPage extends AbstractClientDetailPage<ClientApisDetailPage>
        implements ModalDialog<ClientApisDetailPage> {

    /**
     * table element containing apis
     * @return element
     */
    public SelenideElement apisTable() {
        return $("table[data-field=\"apis\"]");
    }

    /**
     * tr elements containing all apis
     * @return element
     */
    public ElementsCollection allApis() {
        return apisTable().findAll("tbody tr")
                .filter(not(hasAttribute("ng-show", "api.expanded")));
    }

    /**
     * Filter apis from {@link #allApis()} collection by given conditions
     * @param conditions
     * @return element
     */
    public ElementsCollection findApis(Condition... conditions) {
        return allApis().filterBy(Condition.and("Find entries with specific values", conditions));
    }

    public SelenideElement findApi(Condition... conditions) {
        return allApis().find(Condition.and("Find entries with specific values", conditions));
    }

    public SelenideElement howToInvokeButton(Condition... conditions) {
        return findApi(conditions).find(ByApiman.i18n("a", "client-apis.howtoinvoke"));
    }

    public SelenideElement queryParameterInput(Condition... conditions) {
        howToInvokeButton(conditions).click();
        return dialogWindow().find(".modal-body .input-group input");
    }

    public SelenideElement httpHeaderInput(Condition... conditions) {
        howToInvokeButton(conditions).click();
        return dialogWindow().find(".modal-body .input-group:last-of-type input");
    }
}
