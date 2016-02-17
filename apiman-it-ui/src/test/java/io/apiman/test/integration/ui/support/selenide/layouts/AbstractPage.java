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

import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.components.PageComponent;
import io.apiman.test.integration.ui.support.selenide.pages.AboutApimanPage;
import io.apiman.test.integration.ui.support.selenide.pages.HomePage;
import io.apiman.test.integration.ui.support.selenide.pages.LoginPage;
import io.apiman.test.integration.ui.support.selenide.pages.UserProfilePage;
import io.apiman.test.integration.ui.support.selenide.pages.users.UserOrgsListPage;

import com.codeborne.selenide.SelenideElement;

/**
 * Ancestor for all pages which are available only to logged-in users.
 * @author jkaspar
 */
@Layout("/*")
public class AbstractPage<P> implements PageComponent<P> {

    /**
     * Find element containing name of logged user
     * @return element
     */
    public SelenideElement loggedUser() {
        return userMenu().find("span:nth-of-type(2)");
    }

    /**
     * User menu toggle
     * @return element
     */
    public SelenideElement userMenu() {
        return $("#navbar-dropdown");
    }

    /**
     * Home link
     * @return element
     */
    public SelenideElement homeLink() {
        return $("#navbar-home");
    }

    /**
     * Go to Home page
     * @return HomePage
     */
    public HomePage home() {
        userMenu().click();
        homeLink().click();
        return page(HomePage.class);
    }

    /**
     * My stuff link
     * @return element
     */
    public SelenideElement myStuffLink() {
        return $("#navbar-my-stuff");
    }

    /**
     * Go to My Staff page
     * @return UserOrganizationOverviewPage
     */
    public UserOrgsListPage myStuff() {
        userMenu().click();
        myStuffLink().click();
        return page(UserOrgsListPage.class);
    }

    /**
     * Profile link
     * @return element
     */
    public SelenideElement userProfileLink() {
        return $("#navbar-profile");
    }

    /**
     * Go to User profile page
     * @return UserProfilePage
     */
    public UserProfilePage userProfile() {
        userMenu().click();
        userProfileLink().click();
        return page(UserProfilePage.class);
    }

    /**
     * About apiman link
     * @return element
     */
    public SelenideElement aboutApimanLink() {
        return $("#navbar-about");
    }

    /**
     * Go to About apiman page
     * @return AboutApimanPage
     */
    public AboutApimanPage aboutApiman() {
        userMenu().click();
        aboutApimanLink().click();
        return page(AboutApimanPage.class);
    }

    /**
     * Logout link
     * @return element
     */
    public SelenideElement logoutLink() {
        return $("#navbar-logout");
    }

    /**
     * Log out current user
     * @return LoginPage
     */
    public LoginPage logout() {
        userMenu().click();
        logoutLink().click();
        return page(LoginPage.class);
    }

    /**
     * Return this object cast to expected type (specified by Generic parameter P)
     * @return this page object
     */
    @SuppressWarnings("unchecked")
    public P thisPageObject() {
        return (P) this;
    }
}
