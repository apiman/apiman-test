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
import io.apiman.test.integration.ui.support.selenide.components.SelectList;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jcechace
 */
@NoLocation
public class AddIgnoredResourcePolicyPage extends AbstractAddPolicyPage<AddIgnoredResourcePolicyPage>
        implements SelectList<AddIgnoredResourcePolicyPage> {

    @Override
    public AddIgnoredResourcePolicyPage selectPolicyType() {
        return policyType("Ignored Resources Policy");
    }

    @Override
    public SelenideElement elementSelect() {
        return $("#paths");
    }

    /**
     * input used to enter the path to be ignored
     * @return element
     */
    public SelenideElement pathInput() {
        return $("#path");
    }

    /**
     * Add path to the list of ignored paths
     * @param path path to be added
     * @return this page object
     */
    public AddIgnoredResourcePolicyPage ignorePath(String path) {
        pathInput().val(path);
        addOptionButton().click();
        return thisPageObject();
    }
}
