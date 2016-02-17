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

package io.apiman.test.integration.ui.support.selenide.components;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.ByApiman;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

/**
 * @author jcechace
 */
public interface ModalDialog<P> extends PageComponent<P> {

    default SelenideElement dialogWindow() {
        return $(".modal-content");
    }

    default SelenideElement closeDialogButton() {
        return  dialogWindow().$(".close");
    }

    default ElementsCollection buttons() {
        return dialogWindow().$$("button");
    }

    default SelenideElement noDialogButton() {
        return buttons().find(text("no"));
    }

    default SelenideElement yesDialogButton() {
        return buttons().find(text("yes"));
    }

    default SelenideElement doneDialogButton() {
        return dialogWindow().$(ByApiman.i18n("done"));
    }

    /**
     * Dismiss dialog by clicking on X in upper right corner
     * @return this page object
     */
    default P closeDialog() {
        Selenide.sleep(Configuration.timeout);
        closeDialogButton().shouldBe(present);
        closeDialogButton().shouldBe(visible);
        closeDialogButton().click();
        return thisPageObject();
    }

    /**
     * Confirm dialog by clicking on yes button
     * @return this page object
     */
    default P confirmDialog() {
        Selenide.sleep(Configuration.timeout);
        yesDialogButton().click();
        Selenide.sleep(Configuration.timeout); // TODO: possible propagation delay
        return thisPageObject();
    }

    /**
     * dismiss dialog by clicking on X button in upper right corner
     * @return this page object
     */
    default P dismissDialog() {
        Selenide.sleep(Configuration.timeout);
        noDialogButton().click();
        return thisPageObject();
    }


    /**
     * dismiss dialog by clicking on the "Done" button
     * @return this page object
     */
    default P doneWithDialog() {
        Selenide.sleep(Configuration.timeout);
        doneDialogButton().shouldBe(visible);
        doneDialogButton().click();
        dialogWindow().shouldNotBe(visible);
        return thisPageObject();
    }
}
