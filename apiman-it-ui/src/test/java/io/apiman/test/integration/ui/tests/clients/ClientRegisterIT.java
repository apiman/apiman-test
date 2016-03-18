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

package io.apiman.test.integration.ui.tests.clients;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static org.hamcrest.Matchers.equalTo;

import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractClientTest;
import io.apiman.test.integration.ui.support.selenide.pages.clients.detail.ClientDetailPage;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jrumanov
 */
public class ClientRegisterIT extends AbstractClientTest {

    @ClientVersion(client = "client",
        publish = false,
        contracts = {@Contract(vPlan = "planVersion", vApi = "apiVersion")})
    private static ClientVersionBean clientVersion;

    private static ResponseSpecification expectedStatus;
    private static ClientVersions clientVersions;

    @BeforeClass
    public static void setUp() {
        clientVersions = new ClientVersions(client);
        expectedStatus = new ResponseSpecBuilder()
            .expectBody("status", equalTo("Registered"))
            .build();
    }

    @Test
    public void canRegisterClient() {
        ClientDetailPage clientDetailPage = open(ClientDetailPage.class, organization.getId(), client.getId())
            .register();

        clientDetailPage.registerButton().shouldNotBe(visible);
        clientDetailPage.unregisterButton().shouldBe(visible);
        clientDetailPage.reregisterButton().shouldBe(visible);
        clientDetailPage.status().shouldHave(text("Registered"));

        clientVersions.peek(clientVersion.getVersion(), expectedStatus);
    }
}
