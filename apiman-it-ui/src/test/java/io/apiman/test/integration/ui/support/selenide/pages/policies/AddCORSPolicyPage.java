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

import io.apiman.test.integration.ui.support.selenide.components.ItemList;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * @author jkaspar
 */
public class AddCORSPolicyPage extends AbstractAddPolicyPage<AddCORSPolicyPage> {

    @Override
    public AddCORSPolicyPage selectPolicyType() {
        return policyType("CORS Policy");
    }

    public SelenideElement terminateOnCORSErrorSelect() {
        return $("[data-schemapath='root.errorOnCorsFailure'] select");
    }

    public AddCORSPolicyPage terminateOnCORSError(boolean select) {
        terminateOnCORSErrorSelect().selectOption(String.valueOf(select));
        return this;
    }

    public CORSItemList allowOriginList() {
        return new CORSItemList($("[data-schemapath='root.allowOrigin']"), this);
    }

    public SelenideElement allowCredentialsSelect() {
        return $("[data-schemapath='root.allowCredentials'] select");
    }

    public AddCORSPolicyPage allowCredentials(boolean select) {
        allowCredentialsSelect().selectOption(String.valueOf(select));
        return this;
    }

    public CORSItemList exposeHeadersList() {
        return new CORSItemList($("[data-schemapath='root.exposeHeaders']"), this);
    }

    public CORSItemList allowHeadersList() {
        return new CORSItemList($("[data-schemapath='root.allowHeaders']"), this);
    }

    public CORSItemList allowMethodsList() {
        return new CORSItemList($("[data-schemapath='root.allowMethods']"), this);
    }

    public SelenideElement maxAgeInput() {
        return $("[data-schemapath='root.maxAge'] input");
    }

    public AddCORSPolicyPage maxAge(String value) {
        maxAgeInput().val(value);
        return this;
    }

    public static class CORSItemList extends ItemList<AddCORSPolicyPage> {

        public CORSItemList(SelenideElement root, AddCORSPolicyPage thisPageObject) {
            super(root, thisPageObject);
        }

        public CORSItemList(By rootSelector, AddCORSPolicyPage thisPageObject) {
            super(rootSelector, thisPageObject);
        }

        public AddCORSPolicyPage addItem(String value) {
            addItem();
            listedItems().last().find("input").val(value);
            return thisPageObject;
        }
    }

}
