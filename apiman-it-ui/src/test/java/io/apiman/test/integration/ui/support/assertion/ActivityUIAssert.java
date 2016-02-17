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

package io.apiman.test.integration.ui.support.assertion;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;

import io.apiman.test.integration.ui.support.selenide.pages.organizations.OrgActivityListPage;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

/**
 * @author ldimaggi
 */
public class ActivityUIAssert {

    /**
     * Asserts that the most recent activity log entry matches the expected org and activity string
     * as presented in the Organization activity UI page
     * @param org
     * @param expected
     */
    public static void assertActivity(OrganizationBean org, String expected) {
        OrgActivityListPage theActivityPage = open(OrgActivityListPage.class, org.getName());
        theActivityPage.newestDisplayedActivity().shouldHave(text(expected));
    }

}
