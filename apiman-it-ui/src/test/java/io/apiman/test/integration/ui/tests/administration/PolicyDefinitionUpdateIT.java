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

import static com.codeborne.selenide.Condition.*;

import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.assertion.PageAssert;
import io.apiman.test.integration.ui.support.beanutils.PolicyDefinitionUtils;
import io.apiman.test.integration.ui.support.selenide.base.AbstractSimpleUITest;
import io.apiman.test.integration.ui.support.selenide.pages.administration.policies.EditPolicyPage;
import io.apiman.test.integration.ui.support.selenide.pages.administration.policies.PolicyDefsAdminPage;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jrumanov
 */
public class PolicyDefinitionUpdateIT extends AbstractSimpleUITest {

    private static final String NOT_VALID_JSON = "This is not valid json";

    private PolicyDefinitionBean defBean;

    @Before
    public void setUp() {
        defBean = PolicyDefinitionUtils.remote();
    }

    @After
    public void tearDown() {
        PolicyDefinitionUtils.delete(defBean);
    }

    @Test
    public void initialStateOfPage() {
        EditPolicyPage editPolicyPage = open(PolicyDefsAdminPage.class)
            .openEntity(defBean.getName());

        editPolicyPage.updateButton().shouldBe(enabled);
        editPolicyPage.validationErrorMessage().shouldNotBe(visible);
    }

    @Test
    public void shouldDisableButtonOnInvalidJson() {
        EditPolicyPage editPolicyPage = open(PolicyDefsAdminPage.class)
            .openEntity(defBean.getName());
        editPolicyPage.jsonTextField(NOT_VALID_JSON);

        editPolicyPage.updateButton().shouldBe(disabled);
        editPolicyPage.validationErrorMessage().shouldBe(visible);
    }

    @Test
    public void canUpdateNameInNotDefaultPolicy() {
        EditPolicyPage editPolicyPage = open(PolicyDefsAdminPage.class)
            .openEntity(defBean.getName());
        defBean.setName(defBean.getName() + "u");

        String jsonDefinition = new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", defBean.getId())
            .append("policyImpl", defBean.getPolicyImpl())
            .append("name", defBean.getName())
            .append("icon", defBean.getIcon())
            .append("description", defBean.getDescription()).toString();

        editPolicyPage.jsonTextField(jsonDefinition);

        editPolicyPage.updateButton().shouldNotBe(disabled);
        editPolicyPage.validationErrorMessage().shouldNotBe(visible);

        editPolicyPage.updatePolicy();

        PageAssert.assertPolicyDefinitionsDetail(defBean);
        BeanAssert.assertPolicyDefinition(defBean);
    }

    @Test
    public void canUpdateDescriptionInNotDefaultPolicy() {
        EditPolicyPage editPolicyPage = open(PolicyDefsAdminPage.class)
            .openEntity(defBean.getName());

        defBean.setDescription("test description");

        String jsonDefinition = new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", defBean.getId())
            .append("policyImpl", defBean.getPolicyImpl())
            .append("name", defBean.getName())
            .append("icon", defBean.getIcon())
            .append("description", defBean.getDescription()).toString();

        editPolicyPage.jsonTextField(jsonDefinition);

        editPolicyPage.updateButton().shouldNotBe(disabled);
        editPolicyPage.validationErrorMessage().shouldNotBe(visible);

        editPolicyPage.updatePolicy();

        PageAssert.assertPolicyDefinitionsDetail(defBean);
        BeanAssert.assertPolicyDefinition(defBean);
    }

    @Test
    public void canUpdateIconInNotDefaultPolicy() {
        EditPolicyPage editPolicyPage = open(PolicyDefsAdminPage.class)
            .openEntity(defBean.getName());
        defBean.setIcon("eye-slash");

        String jsonDefinition = new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", defBean.getId())
            .append("policyImpl", defBean.getPolicyImpl())
            .append("name", defBean.getName())
            .append("icon", defBean.getIcon())
            .append("description", defBean.getDescription()).toString();

        editPolicyPage.jsonTextField(jsonDefinition);

        editPolicyPage.updateButton().shouldNotBe(disabled);
        editPolicyPage.validationErrorMessage().shouldNotBe(visible);

        editPolicyPage.updatePolicy();

        PageAssert.assertPolicyDefinitionsDetail(defBean);
        BeanAssert.assertPolicyDefinition(defBean);
    }

    @Test
    public void canNotUpdateIdInNotDefaultPolicy() {
        EditPolicyPage editPolicyPage = open(PolicyDefsAdminPage.class)
            .openEntity(defBean.getName());

        String jsonDefinition = new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", defBean.getId() + "u")
            .append("policyImpl", defBean.getPolicyImpl())
            .append("name", defBean.getName())
            .append("icon", defBean.getIcon())
            .append("description", defBean.getDescription()).toString();

        editPolicyPage.jsonTextField(jsonDefinition);

        editPolicyPage.updateButton().shouldBe(disabled);
        editPolicyPage.validationErrorMessage().shouldBe(visible);
    }

    /**
     * this test tests expected behavior when one wants to update default policy
     * (policy that is present by default after clean installation of apiMan)
     * that is - default policy should not be updated -
     * therefor the update button should not be present
     */
    @Test
    public void canNotUpdateDefaultPolicy() {
        EditPolicyPage editPolicyPage = open(PolicyDefsAdminPage.class)
            .openEntity("Authorization Policy");

        editPolicyPage.updateButton().shouldNotBe(present);
        editPolicyPage.jsonTextField().shouldBe(disabled);
    }
}
