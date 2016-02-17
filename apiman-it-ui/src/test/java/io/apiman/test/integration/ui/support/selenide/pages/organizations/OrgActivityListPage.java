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
import io.apiman.test.integration.ui.support.selenide.layouts.OrgEntitiesListPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * @author ldimaggi
 */
@PageLocation("/apimanui/api-manager/orgs/{0}/activity")
public class OrgActivityListPage extends OrgEntitiesListPage<OrgActivityListPage> {

    /* TODO
     * Implementation pending enhancements to Activity page - data export, etc. 
     */

    /**
     * Return all elements - starting at activity root
     * @return
     */
    public SelenideElement activityRoot() {
        return $("#entitytabsContent");
    }

    /**
     * Return the collection of activities from the activity display in the UI
     * @return ElementsCollection
     */
    public ElementsCollection activities() {
        return activityRoot().$$("apiman-audit-entry");
    }

    /**
     * Return the newest activity from the entries displayed from the activity log - the 
     * entry at the top of the activity display in the UI
     * @return SelenideElement
     */
    public SelenideElement newestDisplayedActivity() {
        return activities().get(0);
    }

    /**
     * Return the oldest activity from the entries displayed from the activity log - the 
     * entry at the bottom of the activity display (top page) in the UI
     * @return SelenideElement
     */
    public SelenideElement oldestDisplayedActivity() {
        return activities().get(activities().size()-1);
    }

    /**
     * Return the selected activity from the entries displayed from the activity log -
     * in the (top page) in the UI
     * @return SelenideElement
     */
    public SelenideElement displayedActivity(int counter) {
        return activities().get(counter);
    }

    /**
     * Return the collection of timestamps from the activity display in the UI
     * @return ElementsCollection
     */
    public ElementsCollection timeStamps() {
        return activityRoot().$$("div.apiman-timestamp");
    }

    /**
     * Return the newest timestamp from the entries displayed from the activity log - the 
     * entry at the top of the activity display in the UI
     * @return SelenideElement
     */
    public SelenideElement newestDisplayedTimeStamp() {
        return timeStamps().get(0);
    }

    /**
     * Return the oldest timestamp from the entries displayed from the activity log - the 
     * entry at the bottom of the activity display (top page) in the UI
     * @return SelenideElement
     */
    public SelenideElement oldestDisplayedTimeStamp() {
        return timeStamps().get(timeStamps().size()-1);
    }

    /**
     * Return the selected activity from the entries displayed from the activity log -
     * in the (top page) in the UI
     * @return SelenideElement
     */
    public SelenideElement displayedTimeStamp(int counter) {
        return timeStamps().get(counter);
    }

}
