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

package io.apiman.test.integration.ui.support.selenide.components.administration;

import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.components.PageComponent;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
public interface GatewayForm<P> extends PageComponent<P> {

    default SelenideElement name() {
        return $("#apiman-gateway-name");
    }

    default P name(String value) {
        name().val(value);
        return thisPageObject();
    }

    default SelenideElement configEndpointInput() {
        return $("#config-endpoint");
    }

    default P configEndpoint(String endpoint) {
        configEndpointInput().val(endpoint);
        return thisPageObject();
    }

    default SelenideElement endpointUsernameInput() {
        return $("#test-gateway");
    }

    default P endpointUsername(String value) {
        endpointUsernameInput().val(value);
        return thisPageObject();
    }

    default SelenideElement endpointPasswordInput() {
        return $("#endpoint-password");
    }

    default P endpointPassword(String value) {
        endpointPasswordInput().val(value);
        return thisPageObject();
    }

    default SelenideElement endpointPasswordConfirmInput() {
        return $("#endpoint-password-confirm");
    }

    default P endpointPasswordConfirm(String value) {
        endpointPasswordConfirmInput().val(value);
        return thisPageObject();
    }
}
