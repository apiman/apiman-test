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

package io.apiman.test.integration.ui.support.selenide.pages.organizations;

import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jkaspar, ldimaggi
 */
@PageLocation("/api-manager/orgs/{0}/new-member")
public class OrgNewMemberPage extends AbstractPage {
    
    /**
     * Return document page form root
     * @return SelenideElement
     */
    public SelenideElement formPageRoot () {
        return $("#form-page");
    }
    
    /**
     * Return the roles drop down button within the page form
     * @return 
     */
    public SelenideElement rolesDropDownButton () {
        return formPageRoot().$("button.dropdown-toggle");
    }
    
    /**
     * Return the roles drop down menu within the page form
     * @return SelenideElement
     */
    public SelenideElement rolesDropdown() {
        return formPageRoot().$(".dropdown-menu");
    } 
    
    /**
     * Return the specified/selected role from the roles drop down
     * @param text
     * @return SelenideElement
     */
    public SelenideElement dropDownOption(String text) {
        return rolesDropdown().$$("a").find(Condition.text(text));
    }
    
    /**
     * Return the button (within the page form) used to add roles to a user 
     * @return SelenideElement
     */
    public SelenideElement addRolesButton() {
        return formPageRoot().$("button[data-field=\"addMembersButton\"]");
    } 
    
    /** 
     * Return the search text box (within the page form) 
     * @return SelenideElement
     */
    public SelenideElement searchByUser() {
        return formPageRoot().$(By.xpath("//input[@type='text']"));
    }
    
    /**
     * Return the search submit button (within the page form) 
     * @return SelenideElement
     */
    public SelenideElement submitButton() {
        return formPageRoot().$(By.xpath ("//button[@type='submit']")); 
    }    
    
    /**
     * Return the username selected from the users returned by the search 
     * @param username
     * @return
     */
    public SelenideElement selectUser (String username) {
        return formPageRoot().$(By.partialLinkText(username));
    }
  
}
