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

import com.codeborne.selenide.SelenideElement;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author opontes
 */
public class AddURLRewritingPolicyPage extends AbstractAddPolicyPage<AddURLRewritingPolicyPage> {

    public SelenideElement regex(){
        return $("input[id='fromRegexp']");
    }

    public SelenideElement replacement(){
        return $("input[id='toReplacement']");
    }

    public SelenideElement processHeader(){
        return $("input[id='processHeaders']");
    }

    public SelenideElement processBody(){
        return $("input[id='processBody']");
    }

    public AddURLRewritingPolicyPage configure(
            String regex, String replacement, boolean processHeader, boolean processBody){
        regex().setValue(regex);
        replacement().setValue(replacement);
        if (processHeader) {
            processHeader().click();
        }
        if (processBody) {
            processBody().click();
        }
        return thisPageObject();
    }

    @Override
    public AddURLRewritingPolicyPage selectPolicyType() {
        return policyType("URL Rewriting Policy");
    }
}
