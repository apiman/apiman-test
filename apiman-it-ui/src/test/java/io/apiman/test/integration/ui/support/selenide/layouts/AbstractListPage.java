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

import io.apiman.test.integration.ui.support.selenide.Layout;
import io.apiman.test.integration.ui.support.selenide.components.FilterForm;
import io.apiman.test.integration.ui.support.selenide.components.RowEntries;

import com.codeborne.selenide.SelenideElement;

/**
 * This class represents a entity overview page with listing of child resource
 * (e.g. overview of resources managed by some user or organization)
 *
 * @author jcechace
 */
@Layout({"/users/{id}/*", "/orgs/{id}/*"})
public abstract class AbstractListPage<P> extends AbstractPage<P>
    implements FilterForm<P>, RowEntries {

    /**
     * Entity summary box
     * @return element
     */
    public SelenideElement summary() {
        return $(".apiman-entitysummary");
    }

    /**
     * Header with entity name
     * @return element
     */
    public SelenideElement header() {
        return summary().find(".apiman-header");
    }

    /**
     * Container containing entries
     * @return element
     */
    public SelenideElement entriesContainer() {
        return $("#entitytabsContent");
    }
}
