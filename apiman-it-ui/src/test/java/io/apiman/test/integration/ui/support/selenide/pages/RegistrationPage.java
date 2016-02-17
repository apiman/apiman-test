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

import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Selenide.*;

import io.apiman.test.integration.ui.support.selenide.NoLocation;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jcechace
 */
@NoLocation
public class RegistrationPage {

	/* Web UI Elements */

    /**
     * UI element - register button - on registration page
     * @return element
     */
    public SelenideElement registerCompleteButton() {
        return $("input[value='Register']");
    }

    /**
     * UI element - username
     * @return element
     */
    public SelenideElement username() {
        return $("#username");
    }

    public RegistrationPage username(String username) {
        username().val(username);
        return this;
    }

    /**
     * UI element - firstname
     * @return element
     */
    public SelenideElement firstname() {
        return $("#firstName");
    }

    public RegistrationPage firstname(String firstname) {
        firstname().val(firstname);
        return this;
    }

    /**
     * UI element - lastname
     * @return element
     */
    public SelenideElement lastname() {
        return $("#lastName");
    }

    public RegistrationPage lastname(String lastname) {
        lastname().val(lastname);
        return this;
    }

    /**
     * UI element - email
     * @return element
     */
    public SelenideElement email() {
        return $("#email");
    }

    public RegistrationPage email(String email) {
        email().val(email);
        return this;
    }

    /**
     * UI element - password
     * @return element
     */
    public SelenideElement password() {
        return $("#password");
    }

    public RegistrationPage password(String password) {
        password().val(password);
        return this;
    }

    /**
     * UI element - password confirm
     * @return element
     */
    public SelenideElement passwordConfirm() {
        return $("#password-confirm");
    }

    public RegistrationPage passwordConfirm(String password) {
        passwordConfirm().val(password);
        return this;
    }

    /**
     * UI element - street address
     * @return element
     */
    public SelenideElement street() {
        return $("#user.attributes.street");
    }

    /**
     * UI element - locality/city
     * @return element
     */
    public SelenideElement locality() {
        return $("#user.attributes.locality");
    }

    /**
     * UI element - region
     * @return element
     */
    public SelenideElement region() {
        return $("#user.attributes.region");
    }

    /**
     * UI element - postal code
     * @return element
     */
    public SelenideElement postalCode() {
        return $("#user.attributes.postal_code");
    }

    /**
     * UI element - country
     * @return element
     */
    public SelenideElement country() {
        return $("#user.attributes.country");
    }

    public RegistrationPage country(String country) {
        country().val(country);
        return this;
    }

    /**
     * Register a new user in the UI - complete the registration
     *
     * @return page
     */
    public HomePage registerComplete() {
        registerCompleteButton().click();
        return page(HomePage.class);
    }

    public LoginPage cancel() {
        $(By.partialLinkText("Back to Login")).click();
        return page(LoginPage.class);
    }

    public boolean isCurrentPage() {
        return title().contains("Register with apiman") && registerCompleteButton().is(present);
    }

}
