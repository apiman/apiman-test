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

package io.apiman.test.integration.ui.support.selenide.pages.policies;

import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.components.ItemList;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.SelenideElement;

/**
 * Created by jsmolar.
 */
public class AddCircuitBreakerPluginPolicyPage extends AbstractAddPolicyPage<AddCircuitBreakerPluginPolicyPage> {

    private SelenideElement timeWindowInput() {
        return $("input[name=\"root[window]\"]");
    }

    private SelenideElement limitInput() {
        return $("input[name=\"root[limit]\"]");
    }

    private SelenideElement resetInput() {
        return $("input[name=\"root[reset]\"]");
    }

    private SelenideElement failureCodeInput() {
        return $("input[name=\"root[failureCode]\"]");
    }

    public ErrorCodesItemList errorCodesList() {
        return new ErrorCodesItemList($("[data-schemapath='root.errorCodes']"), this);
    }

    public AddCircuitBreakerPluginPolicyPage timeWindow(Integer window) {
        timeWindowInput().setValue(window.toString());

        return thisPageObject();
    }

    public AddCircuitBreakerPluginPolicyPage limit(Integer limit) {
        limitInput().setValue(limit.toString());

        return thisPageObject();
    }

    public AddCircuitBreakerPluginPolicyPage reset(Integer reset) {
        resetInput().setValue(reset.toString());

        return thisPageObject();
    }

    public AddCircuitBreakerPluginPolicyPage failureCode(Integer failureCode) {
        failureCodeInput().setValue(failureCode.toString());

        return thisPageObject();
    }

    @Override
    public AddCircuitBreakerPluginPolicyPage selectPolicyType() {
        return policyType("Circuit Breaker Policy");
    }

    public static class ErrorCodesItemList extends ItemList<AddCircuitBreakerPluginPolicyPage> {

        public ErrorCodesItemList(SelenideElement root, AddCircuitBreakerPluginPolicyPage thisPageObject) {
            super(root, thisPageObject);
        }

        public AddCircuitBreakerPluginPolicyPage addErrorCode(String value) {
            addItem();
            listedItems().last().find("input").val(value);
            return thisPageObject;
        }

        /**
         * number rows is maximum 9 because of error code format
         */
        public AddCircuitBreakerPluginPolicyPage addErrorCodes(int numberOfRows) {

            int size = listedItems().size();
            for (int i = size; i < size + numberOfRows; i++) {
                listedItems().last().find("input").val("**" + i);
                if (i != size + numberOfRows - 1) {
                    addItem();
                }
            }

            return thisPageObject;
        }
    }
}
