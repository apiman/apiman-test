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
import io.apiman.test.integration.ui.support.selenide.components.FilterForm;
import io.apiman.test.integration.ui.support.selenide.components.RowEntries;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jcechace
 */
@PageLocation("/apimanui/api-manager/browse/orgs/{0}")
public class BrowseOrgDetailPage extends AbstractPage<BrowseOrgDetailPage> implements
    FilterForm<BrowseOrgDetailPage>,
    RowEntries {

    /**
     * Container holding information (name, description) about this organization
     * @return element
     */
    public SelenideElement orgContainer() {
        return $(".browse-items .item");
    }

    /**
     * Name of the organization displayed on this page
     * @return element
     */
    public SelenideElement orgTitle() {
        return orgContainer().find(".item [data-field='title']");
    }

    /**
     * Description of the organization displayed on this page
     * @return element
     */
    public SelenideElement orgDescription() {
        return orgContainer().find(".description");
    }


    @Override
    public SelenideElement entriesContainer() {
        return $(".apiman-apis");
    }

    // Members

    /**
     * Members container
     * @return element
     */
    public SelenideElement membersContainer() {
        return $(".apiman-members");
    }

    /**
     * Collection of members
     * @return elements collection
     */
    public ElementsCollection membersEntries() {
        return membersContainer().findAll(".apiman-summaryrow");
    }

    /**
     * Link to member by given name
     * @param name value
     * @return element
     */
    public SelenideElement memberLink(String name) {
        return membersContainer().find(By.partialLinkText(name));
    }
}
