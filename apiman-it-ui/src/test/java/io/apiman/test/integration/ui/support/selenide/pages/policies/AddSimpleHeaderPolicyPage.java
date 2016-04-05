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

import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import com.codeborne.selenide.SelenideElement;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class AddSimpleHeaderPolicyPage extends AbstractAddPolicyPage<AddSimpleHeaderPolicyPage> {

    /**
     * Enum representing Value Type select options
     */
    public enum ValueType {
        Env("Env"),
        String("String"),
        System("System Properties");

        private final String text;

        ValueType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * Enum representing Apply to select options
     */
    public enum ApplyTo {
        Request("Request"),
        Response("Response"),
        Both("Both");

        private final String text;

        ApplyTo(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    /**
     * Enum representing Header type select options
     */
    public enum HeaderType {
        Key("Key"),
        Value("Value");

        private final String text;

        HeaderType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * Enum representing WithMatcher select options
     */
    public enum WithMatcher {
        String("String"),
        Regex("Regex");

        private final String text;

        WithMatcher(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private int addHeaderCount;
    private int stripHeaderCount;

    public void clickAddHeader() {
        $("button[title=\"Add Header\"]").click();
    }

    public void clickStripHeader() {
        $("button[title=\"Add Strip Headers\"]").click();
    }

    public SelenideElement headerNameTextBox(int number) {
        return $("input[name=\"root[addHeaders][" + number + "][headerName]\"]");
    }

    public SelenideElement moveUpButton(int number) {
        return $("button[title=\"Move up\"][data-i=\"" + number + "\"]");
    }

    public SelenideElement deleteLastButton() {
        return $("button[title=\"Delete Last Header\"]");
    }


    public SelenideElement getMoveDownButton(int number) {
        return $("button[title=\"Move down\"][data-i=\"" + number + "\"]");
    }

    public SelenideElement deleteButton(int number) {
        return $("button[title=\"Delete\"][data-i=\"" + number + "\"]");
    }

    public SelenideElement headerValueTextBox(int number) {
        return $("input[name=\"root[addHeaders][" + number + "][headerValue]\"]");
    }

    public SelenideElement headerValueTypeSelect(int number) {
        return $("select[name=\"root[addHeaders][" + number + "][valueType]\"]");
    }

    public SelenideElement headerApplyToSelect(int number) {
        return $("select[name=\"root[addHeaders][" + number + "][applyTo]\"]");
    }

    public SelenideElement headerOverwriteSelect(int number) {
        return $("select[name=\"root[addHeaders][" + number + "][overwrite]\"]");
    }

    public SelenideElement stripMatchTypeSelect(int number) {
        return $("select[name=\"root[stripHeaders][" + number + "][stripType]\"]");
    }

    public SelenideElement stripMatchWithSelect(int number) {
        return $("select[name=\"root[stripHeaders][" + number + "][with]\"]");
    }

    public SelenideElement stripMatchPatternText(int number) {
        return $("input[name=\"root[stripHeaders][" + number + "][pattern]\"]");
    }

    public AddSimpleHeaderPolicyPage addHeader(
        String name, String value, ValueType valueType, ApplyTo applyTo, Boolean overwrite) {

        clickAddHeader();
        headerNameTextBox(addHeaderCount).setValue(name);
        headerValueTextBox(addHeaderCount).setValue(value);
        headerValueTypeSelect(addHeaderCount).selectOption(valueType.toString());
        headerApplyToSelect(addHeaderCount).selectOption(applyTo.toString());
        headerOverwriteSelect(addHeaderCount).selectOption(overwrite.toString());
        addHeaderCount++;

        return thisPageObject();
    }

    public AddSimpleHeaderPolicyPage deletedHeader(int index) {

        deleteButton(index).click();
        addHeaderCount--;

        return thisPageObject();
    }

    public int getAddHeaderCount() {
        return addHeaderCount;
    }

    public int getStripHeaderCount() {
        return stripHeaderCount;
    }

    public AddSimpleHeaderPolicyPage stripHeader(
        HeaderType headerType, WithMatcher withMatcher, String pattern) {

        clickStripHeader();
        stripMatchTypeSelect(stripHeaderCount).selectOption(headerType.toString());
        stripMatchWithSelect(stripHeaderCount).selectOption(withMatcher.toString());
        stripMatchPatternText(stripHeaderCount).setValue(pattern);
        stripHeaderCount++;

        return thisPageObject();
    }

    @Override
    public AddSimpleHeaderPolicyPage selectPolicyType() {
        return policyType("Simple Header Policy");
    }
}
