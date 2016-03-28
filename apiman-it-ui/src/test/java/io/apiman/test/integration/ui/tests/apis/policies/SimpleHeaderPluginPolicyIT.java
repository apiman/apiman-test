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

package io.apiman.test.integration.ui.tests.apis.policies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddSimpleHeaderPolicyPage;

import com.codeborne.selenide.Condition;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by pstanko.
 * @author pstanko
 */
@Plugin(artifactId = "apiman-plugins-simple-header-policy")
public class SimpleHeaderPluginPolicyIT extends AbstractApiPolicyIT {

    private AddSimpleHeaderPolicyPage addPolicyPage;

    private final int NUM_HEAD = 3;

    private void addHeaders() {
        for (int i = 0; i < NUM_HEAD; i++) {
            addPolicyPage.
                addHeader("X-Header-" + i, "Value" + i,
                    AddSimpleHeaderPolicyPage.ValueType.String,
                    AddSimpleHeaderPolicyPage.ApplyTo.Both, true);
        }
        assertThat(addPolicyPage.getAddHeaderCount(), equalTo(3));
    }

    @Before
    public void openPage() {
        addPolicyPage = policiesDetailPage.addPolicy(AddSimpleHeaderPolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.SIMPLE_HEADER_POLICY;
    }

    @Test
    public void shouldAddRequestPolicy() {
        addPolicyPage
            .addHeader("X-Request", "Request value",
                AddSimpleHeaderPolicyPage.ValueType.String, AddSimpleHeaderPolicyPage.ApplyTo.Request, true)
            .addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
        assertThat(addPolicyPage.getAddHeaderCount(), equalTo(1));
    }

    @Test
    public void shouldBeDisabledAddButton() {
        addPolicyPage.clickAddHeader();
        addPolicyPage.addPolicyButton().shouldBe(Condition.disabled);
    }

    @Test
    public void shouldStripValueByRegexPolicy() {
        addPolicyPage
            .stripHeader(AddSimpleHeaderPolicyPage.HeaderType.Value, AddSimpleHeaderPolicyPage.WithMatcher.Regex,
                "ahoj")
            .addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
        assertThat(addPolicyPage.getStripHeaderCount(), equalTo(1));
    }

    @Test
    public void shouldDeleteHeaderPolicyFromAddHeaderByClickDeleteButton() {
        addHeaders();

        addPolicyPage.deletedHeader(1);
        assertThat(addPolicyPage.getAddHeaderCount(), equalTo(NUM_HEAD - 1));
        addPolicyPage.addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
    }

    @Test
    public void shouldAdd3AddHeaders() {
        addHeaders();
        addPolicyPage.addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
    }

    @Test
    public void shouldMoveHeaderUp() {
        addHeaders();
        addPolicyPage.getMoveUpButton(1).click();
        addPolicyPage.addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
    }

    @Test
    public void shouldMoveHeaderDown() {
        addHeaders();
        addPolicyPage.getMoveDownButton(1).click();
        addPolicyPage.addPolicy(AddSimpleHeaderPolicyPage.class);
        assertPolicyPresent();
    }

}
