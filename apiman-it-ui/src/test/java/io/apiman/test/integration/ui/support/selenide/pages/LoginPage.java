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

import com.codeborne.selenide.SelenideElement;

/**
 * @author jcechace
 */
public class LoginPage {

    /**
     * Username input
     * @return element
     */
    public SelenideElement username() {
        return $("#username");
    }

    /**
     * Password input
     * @return element
     */
    public SelenideElement password() {
        return $("#password");
    }

    /**
     * Login button
     * @return element
     */
    public SelenideElement loginButton() {
        return $("input[name='login']");
    }

    /**
     * Cancel button
     *
     * @return
     */
    public SelenideElement cancelButton() {
        return $("#kc-registration a");
    }

    /**
     * Performs the login procedure with given username and password
     * @param usr username
     * @param pswd password
     * @return HomePage page object
     */
    public HomePage login(String usr, String pswd) {
        return login(usr, pswd, HomePage.class);
    }

    /**
     * Follows the registration link
     * @return RegistrationPage page object
     */
    public RegistrationPage registration() {
        cancelButton().click();
        return page(RegistrationPage.class);
    }

    /**
     *
     * @return true if current page is login page
     */
    public boolean isCurrentPage() {
        return title().contains("Log in to apiman") && loginButton().is(present);
    }

    /**
     * Performs the login procedure with given username and password
     * @param usr username
     * @param password password
     * @param pageObjectClassClass class representing the following page
     * @return following page object
     */
    public <F> F login(String usr, String password, Class<F> pageObjectClassClass) {
        username().val(usr);
        password().val(password);
        loginButton().click();
        return page(pageObjectClassClass);
    }
}
