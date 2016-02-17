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

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.components.ModalDialog;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@Layout("/orgs/{id}/*/{id}/{version}/*")
public abstract class AbstractDetailPage<P> extends AbstractPage<P> implements
    ModalDialog<P> {

    /**
     * Entity name
     * @return element
     */
    public SelenideElement name() {
        return $(".apiman-entity-summary .title a");
    }

    /**
     * Entity description
     * @return element
     */
    public SelenideElement description() {
        return $("#descriptionWrapper");
    }

    /**
     * Entity status
     * @return element
     */
    public SelenideElement status() {
        return $(".apiman-entity-summary [apiman-entity-status]");
    }

    /**
     * Dropdown button for version selection
     * @return element
     */
    public SelenideElement versionSelectorButton() {
        return $(".versions button[data-toggle='dropdown']");
    }

    /**
     * Dropdown menu of version selection
     * @return element
     */
    public SelenideElement versionSelectorDropDown() {
        return $(".versions .dropdown-menu");
    }

    /**
     * Options in the version selection
     * @return element collection
     */
    public ElementsCollection versionSelectorOptions() {
        return versionSelectorDropDown().findAll("a");
    }

    /**
     * Element holding selected version
     * @return element
     */
    public SelenideElement version() {
        return versionSelectorButton();
    }

    /**
     * Picks the value in version selector
     * @param name value
     * @return this page object
     */
    public P version(String name) {
        versionSelectorButton().click();
        versionSelectorOptions().findBy(exactText(name)).click();
        return thisPageObject();
    }

    /**
     * New version link
     * @return element
     */
    public SelenideElement newVersionLink() {
        return $("a[data-field='ttd_toNewVersion']");
    }

    /**
     * Click the new version link
     * @param pageClass representing following page
     * @return following page object
     */
    public <F> F newVersion(Class<F> pageClass) {
        newVersionLink().click();
        return page(pageClass);
    }

    // Sidebar Tabs

    /**
     * Element pointing to policies tab
     * @return element
     */
    public SelenideElement policiesTab() {
        return $("#tab-policies");
    }

    /**
     * Element pointing to activity tab
     * @return element
     */
    public SelenideElement activityTab() {
        return $("#tab-activity");
    }

    /**
     * Switch to policies management tab
     * @return FollowingPageObject
     */
    public <F> F policies(Class<F> pageClass) {
        policiesTab().click();
        return page(pageClass);
    }

    /**
     * Switch to activity management tab
     * @return FollowingPageObject
     */
    public <F> F activity(Class<F> pageClass) {
        activityTab().click();
        return page(pageClass);
    }
}
