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

import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractDetailPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * Created by Jarek Kaspar, ldimaggi
 */
@Layout
@PageLocation("/apimanui/api-manager/orgs/{0}/plans/{1}/{2}/policies")
public class PoliciesDetailPage extends AbstractDetailPage<PoliciesDetailPage> {
    
    /**
     * Return the root element for all policies
     * @return SelenideElement
     */
    public SelenideElement policiesRoot () {
        return $("apiman-policy-list");
    } 
    
    /**
     * Return the collection of policies
     * @return ElementsCollection
     */
    public ElementsCollection policies() {
        return policiesRoot().$$("div.container-fluid");
    }
    
    /**
     * Return the number of policies in the collection
     * @return int
     */
    public int policiesCount () {
        return policies().size();
    }
    
    /** 
     * Return the polict title string (from the HTML anchor)
     * @param startPos
     * @return The policy name string
     */
    public SelenideElement policyTitle (SelenideElement startPos) {
        return startPos.$("a.ng-binding");        
    }    
    
}
