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
import io.apiman.test.integration.ui.support.selenide.components.SelectList;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@NoLocation
public class AddStaticBASICAuthenticationPolicyPage
        extends AddBASICAuthenticationPolicyPage<AddStaticBASICAuthenticationPolicyPage>
        implements SelectList<AddStaticBASICAuthenticationPolicyPage> {

    @Override
    public AddStaticBASICAuthenticationPolicyPage selectIdentitySource() {
        return identitySource("Static");
    }

    @Override
    public SelenideElement elementSelect() {
        return $("#static-identities");
    }

    /**
     * Input used for adding new user into Static Identities table
     * @return element
     */
    public SelenideElement usernameInput() {
        return $("#static-username");
    }

    /**
     * Set value into {@link #usernameInput()}
     * @param location value
     * @return this page object
     */
    public AddStaticBASICAuthenticationPolicyPage username(String location) {
        usernameInput().val(location);
        return this;
    }

    /**
     * Input used for adding new user into Static Identities table
     * @return element
     */
    public SelenideElement passwordInput() {
        return $("#static-password");
    }

    /**
     * Set value into {@link #passwordInput()}
     * @param location value
     * @return this page object
     */
    public AddStaticBASICAuthenticationPolicyPage password(String location) {
        passwordInput().val(location);
        return this;
    }

    /**
     * Helpful method used for adding new user into Static Identities select
     * @param name of new user
     * @param password of new
     * @return this page object
     */
    public AddStaticBASICAuthenticationPolicyPage addIdentity(String name, String password) {
        username(name);
        password(password);
        addOptionButton().click();
        return this;
    }
}
