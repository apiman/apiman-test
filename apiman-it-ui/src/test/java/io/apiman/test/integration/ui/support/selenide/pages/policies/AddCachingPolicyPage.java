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

import io.apiman.test.integration.ui.support.selenide.NoLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@NoLocation
public class AddCachingPolicyPage extends AbstractAddPolicyPage<AddCachingPolicyPage> {

    /**
     * Time to live input
     * @return element
     */
    public SelenideElement timeToLiveInput() {
        return $("#ttl");
    }

    /**
     * Set value into {@link #timeToLiveInput()}
     * @param seconds count
     * @return this page object
     */
    public AddCachingPolicyPage timeToLive(int seconds) {
        timeToLiveInput().val(String.valueOf(seconds));
        return this;
    }

    @Override
    public AddCachingPolicyPage selectPolicyType() {
        return policyType("Caching Policy");
    }
}
