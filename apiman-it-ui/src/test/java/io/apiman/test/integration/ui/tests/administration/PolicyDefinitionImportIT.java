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

package io.apiman.test.integration.ui.tests.administration;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.visible;

import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.PolicyDefinitionUtils;
import io.apiman.test.integration.ui.support.selenide.base.AbstractUITest;
import io.apiman.test.integration.ui.support.selenide.pages.administration.policies.ImportPolicyPage;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jrumanov
 */
public class PolicyDefinitionImportIT extends AbstractUITest {

    private PolicyDefinitionBean defBean;
    private ImportPolicyPage importPolicyPage;
    private String jsonDefinition;

    private static final String NOT_VALID_JSON = "This is not valid json";

    @Before
    public void initialize() {
        defBean = PolicyDefinitionUtils.local();

        jsonDefinition = new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", defBean.getId())
            .append("policyImpl", defBean.getPolicyImpl())
            .append("name", defBean.getName())
            .append("icon", defBean.getIcon())
            .append("description", defBean.getDescription()).toString();

        importPolicyPage = open(ImportPolicyPage.class);
    }

    @Test
    public void shouldShowDisabledButtonForInitialStateOfPage() {
        importPolicyPage.validationErrorMessage().shouldNotBe(visible);
        importPolicyPage.importButton().shouldBe(disabled);
    }

    @Test
    public void shouldNotDipslayValidationErrorForValidDefinition() {
        importPolicyPage.jsonText(jsonDefinition);

        importPolicyPage.validationErrorMessage().shouldNotBe(visible);
        importPolicyPage.importButton().shouldNotBe(disabled);
    }

    @Test
    public void canCreateValidPolicyDefinition() {
        importPolicyPage.jsonText(jsonDefinition);
        importPolicyPage.importPolicyDefinition();

        PageAssert.assertPolicyDefinitionsDetail(defBean);
        BeanAssert.assertPolicyDefinition(defBean);
    }

    @Test
    public void canNotUseInvalidPolicyDefinition() {
        importPolicyPage.jsonText(NOT_VALID_JSON);

        importPolicyPage.validationErrorMessage().shouldBe(visible);
        importPolicyPage.importButton().shouldBe(disabled);
    }

    @Test
    public void canNotUseEmptyPolicyDefinition() {
        importPolicyPage.jsonText("");

        importPolicyPage.validationErrorMessage().shouldNotBe(visible);
        importPolicyPage.importButton().shouldBe(disabled);
    }
}
