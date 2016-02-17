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

import io.apiman.test.integration.ui.support.selenide.NoLocation;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@NoLocation
public class AddTransferQuotaPolicyPage extends AddLimitingPolicyPage<AddTransferQuotaPolicyPage> {

    /**
     * UI element - div enclosing transfer direction dropdown
     * TODO: element is missing identifier
     * @return element
     */
    public SelenideElement transferDirectionRoot() {
        return transferDirectionDropdownButton().parent();
    }

    /**
     * UI element - dropdown for transfer direction
     * @return element
     */
    public SelenideElement transferDirectionDropdownButton() {
        return $("button[data-id='direction']");
    }

    /**
     * Dropdown listing transfer direction options
     * @return element
     */
    public SelenideElement transferDirectionDropdown() {
        return transferDirectionRoot().find(".dropdown-menu");
    }

    /**
     * Options listed in {@link #transferDirectionDropdown()} ()}
     * @param text text of the option
     * @return element
     */
    public SelenideElement transferDirectionDropdownOption(String text) {
        return transferDirectionDropdown().findAll("a").find(Condition.text(text));
    }

    /**
     * Select transfer direction {@link #transferDirectionDropdown()} ()}
     * @param direction displayed direction text label
     * @return this page object
     */
    public AddTransferQuotaPolicyPage transferDirection(String direction) {
        transferDirectionDropdownButton().click();
        transferDirectionDropdownOption(direction).click();
        return thisPageObject();
    }

    /**
     * Limit denomination selector
     * @return select element
     */
    public SelenideElement limitDenominationSelect() {
        return $("#denomination");
    }

    /**
     * Select option in denomination selector by visible text of the option
     * @param text of the option
     * @return this page object
     */
    public AddTransferQuotaPolicyPage limitDenomination(String text) {
        limitDenominationSelect().selectOption(text);
        return this;
    }

    public AddTransferQuotaPolicyPage configure(int limit, String granularity, String period,
            String limitDenomination, String transferDirection) {
        limitDenomination(limitDenomination);
        transferDirection(transferDirection);
        return super.configure(limit, granularity, period);
    }

    @Override
    public SelenideElement limitAmountInput() {
        return $("#num-bytes");
    }

    @Override
    public AddTransferQuotaPolicyPage selectPolicyType() {
        return policyType("Transfer Quota Policy");
    }
}
