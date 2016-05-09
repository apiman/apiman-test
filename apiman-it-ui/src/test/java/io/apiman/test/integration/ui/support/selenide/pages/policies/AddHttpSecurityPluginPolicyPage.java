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
 * Created by jsmolar.
 *
 * UI test for Http Security where hsts = HTTP Strict Transport Security
 * and csp = Content Security Policy
 */
public class AddHttpSecurityPluginPolicyPage extends AbstractAddPolicyPage<AddHttpSecurityPluginPolicyPage> {

    public enum CspMode {
        Enabled("ENABLED"),
        ReportOnly("REPORT_ONLY"),
        Disabled("DISABLED");

        private final String text;

        CspMode(final String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return text;
        }
    }

    public enum FrameOptions {
        Deny("DENY"),
        SameOrigin("SAMEORIGIN"),
        Disabled("DISABLED");

        private final String text;

        FrameOptions(final String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return text;
        }
    }

    public enum XssProtection {
        On("ON"),
        Off("OFF"),
        Block("BLOCK"),
        Disabled("DISABLED");

        private final String text;

        XssProtection(final String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return text;
        }
    }

    private SelenideElement hstsEnabledSelect(){
        return $("select[name=\"root[hsts][enabled]\"]");
    }

    private SelenideElement hstsIncludeSubdomainsSelect(){
        return $("select[name=\"root[hsts][includeSubdomains]\"]");
    }

    private SelenideElement hstsMaxAgeInput(){
        return $("input[name=\"root[hsts][maxAge]\"]");
    }

    private SelenideElement hstsPreloadSelect(){
        return $("select[name=\"root[hsts][preload]\"]");
    }

    private SelenideElement cspModeSelect(){
        return $("select[name=\"root[contentSecurityPolicy][mode]\"]");
    }

    private SelenideElement cspTextarea(){
        return $("textarea[name=\"root[contentSecurityPolicy][csp]\"]");
    }

    private SelenideElement frameOptionsSelect(){
        return $("select[name=\"root[frameOptions]\"]");
    }

    private SelenideElement xssProtectionSelect(){
        return $("select[name=\"root[xssProtection]\"]");
    }

    private SelenideElement contentTypeOptionsSelect(){
        return $("select[name=\"root[contentTypeOptions]\"]");
    }

    public AddHttpSecurityPluginPolicyPage hstsEnabled(Boolean selectOption){
        hstsEnabledSelect().selectOption(selectOption.toString());

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage hstsIncludeSubdomains(Boolean includeSubdomains) {
        hstsIncludeSubdomainsSelect().selectOption(includeSubdomains.toString());

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage hstsMaxAge(Integer maxAge){
        hstsMaxAgeInput().setValue(maxAge.toString());

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage hstsPreload(Boolean preload){
        hstsPreloadSelect().selectOption(preload.toString());

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage cspMode(CspMode mode){
        cspModeSelect().selectOption(mode.toString());

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage csp(String csp){
        cspTextarea().setValue(csp);

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage frameOptions(FrameOptions frameOptions){
        frameOptionsSelect().selectOption(frameOptions.toString());

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage xssProtection(XssProtection xss){
        xssProtectionSelect().selectOption(xss.toString());

        return thisPageObject();
    }

    public AddHttpSecurityPluginPolicyPage contentTypeOptions(Boolean contentTypeOptions) {
        contentTypeOptionsSelect().selectOption(contentTypeOptions.toString());

        return thisPageObject();
    }

    @Override
    public AddHttpSecurityPluginPolicyPage selectPolicyType() {
        return policyType("HTTP Security Policy");
    }
}
