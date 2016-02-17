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

package io.apiman.test.integration.ui.support.selenide.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

import com.codeborne.selenide.SelenideElement;

/**
 * Created by Jarek Kaspar
 */
@PageLocation("/apimanui/api-manager/profile")
public class UserProfilePage extends AbstractPage {

    /**
     * Username input
     * @return element
     */
    public SelenideElement userNameInput() {
        return $("#apiman-username");
    }

    /**
     * Set user name into user name input
     * @param name
     * @return this page object
     */
    public UserProfilePage userName(String name) {
        userNameInput().val(name);
        return this;
    }

    /**
     * Full name input
     * @return element
     */
    public SelenideElement fullNameInput() {
        return $("#apiman-name");
    }

    /**
     * Set full name into full name input
     * @param name
     * @return this page object
     */
    public UserProfilePage fullName(String name) {
        userNameInput().val(name);
        return this;
    }

    /**
     * Email input
     * @return element
     */
    public SelenideElement emailInput() {
        return $("#apiman-email");
    }

    /**
     * Set email into email input
     * @param email
     * @return this page object
     */
    public UserProfilePage email(String email) {
        userNameInput().val(email);
        return this;
    }

    // Form buttons

    /**
     * Create button
     * @return element
     */
    public SelenideElement updateButton() {
        return $("button[data-field='updateButton']");
    }

    /**
     * Click the create button
     * @return this page object
     */
    public UserProfilePage update() {
        updateButton().click();
        return this;
    }

    /**
     * Cancel button
     * @return element
     */
    public SelenideElement cancelButton() {
        return $("#cancel");
    }

    /**
     * Click the cancel button
     * @param pageClass class representing previous page
     * @return previous page object
     */
    public  <R> R cancel(Class<R> pageClass) {
        cancelButton().click();
        return page(pageClass);
    }
}
