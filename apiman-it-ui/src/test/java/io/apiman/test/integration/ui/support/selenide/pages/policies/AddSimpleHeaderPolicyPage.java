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

    public enum ValueType {
        Env("Env"),
        String("String"),
        System("System Properties");

        private final String text;

        /**
         * @param text
         */
        ValueType(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }

    public enum ApplyTo {
        Request("Request"),
        Response("Response"),
        Both("Both");

        private final String text;

        /**
         * @param text
         */
        ApplyTo(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }

    }

    public enum HeaderType {
        Key("Key"),
        Value("Value");

        private final String text;

        /**
         * @param text
         */
        HeaderType(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }

    public enum WithMatcher {
        String("String"),
        Regex("Regex");

        private final String text;

        /**
         * @param text
         */
        WithMatcher(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
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

    public SelenideElement getHeaderNameTextBox(int number) {
        return $("input[name=\"root[addHeaders][" + number + "][headerName]\"]");
    }

    public SelenideElement getMoveUpButton(int number) {
        return $("button[title=\"Move up\"][data-i=\"" + number + "\"]");
    }

    public SelenideElement getDeleteLastButton() {
        return $("button[title=\"Delete Last Header\"]");
    }

    public SelenideElement getDeleteAllButton() {
        return $("button[title=\"Delete All\"]");
    }

    public SelenideElement getMoveDownButton(int number) {
        return $("button[title=\"Move down\"][data-i=\"" + number + "\"]");
    }

    public SelenideElement getDeleteButton(int number) {
        return $("button[title=\"Delete\"][data-i=\"" + number + "\"]");
    }

    public SelenideElement getHeaderValueTextBox(int number) {
        return $("input[name=\"root[addHeaders][" + number + "][headerValue]\"]");
    }

    public SelenideElement getHeaderValueTypeSelect(int number) {
        return $("select[name=\"root[addHeaders][" + number + "][valueType]\"]");
    }

    public SelenideElement getHeaderApplyToSelect(int number) {
        return $("select[name=\"root[addHeaders][" + number + "][applyTo]\"]");
    }

    public SelenideElement getHeaderOverwriteSelect(int number) {
        return $("select[name=\"root[addHeaders][" + number + "][overwrite]\"]");
    }

    public SelenideElement getStripMatchTypeSelect(int number) {
        return $("select[name=\"root[stripHeaders][" + number + "][stripType]\"]");
    }

    public SelenideElement getStripMatchWithSelect(int number) {
        return $("select[name=\"root[stripHeaders][" + number + "][with]\"]");
    }

    public SelenideElement getStripMatchPatternText(int number) {
        return $("input[name=\"root[stripHeaders][" + number + "][pattern]\"]");
    }

    public AddSimpleHeaderPolicyPage addHeader(
        String name, String value, ValueType valueType, ApplyTo applyTo, Boolean overwrite) {

        clickAddHeader();
        getHeaderNameTextBox(addHeaderCount).setValue(name);
        getHeaderValueTextBox(addHeaderCount).setValue(value);
        getHeaderValueTypeSelect(addHeaderCount).selectOption(valueType.toString());
        getHeaderApplyToSelect(addHeaderCount).selectOption(applyTo.toString());
        getHeaderOverwriteSelect(addHeaderCount).selectOption(overwrite.toString());
        addHeaderCount++;

        return thisPageObject();
    }

    public AddSimpleHeaderPolicyPage deletedHeader(int index) {

        getDeleteButton(index).click();
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
        getStripMatchTypeSelect(stripHeaderCount).selectOption(headerType.toString());
        getStripMatchWithSelect(stripHeaderCount).selectOption(withMatcher.toString());
        getStripMatchPatternText(stripHeaderCount).setValue(pattern);
        stripHeaderCount++;

        return thisPageObject();
    }

    @Override
    public AddSimpleHeaderPolicyPage selectPolicyType() {
        return policyType("Simple Header Policy");
    }
}
