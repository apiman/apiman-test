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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.test.integration.ui.support.selenide.base.AbstractClientTest;
import io.apiman.test.integration.ui.support.selenide.pages.clients.CreateClientVersionPage;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jrumanov
 */
public class CloneClientVersionIT extends AbstractClientTest {

    private static final String CLONED_NAME = "2.0";

    private static ClientVersions clientVersions;

    @ClientVersion(client = "client", contracts = {@Contract(vPlan = "planVersion", vApi = "apiVersion")},
        policies = @Policies("arbitrary"))
    private static ClientVersionBean version;

    @BeforeClass
    public static void setUp() {
        clientVersions = new ClientVersions(client);
    }

    @Test
    public void canCloneClientVersion() throws InterruptedException {
        CreateClientVersionPage createVersionPage =
            open(CreateClientVersionPage.class, organization.getId(), client.getId(), version.getVersion());

        createVersionPage
            .version(CLONED_NAME)
            .clone(true)
            .create();

        clientVersions.peek(CLONED_NAME);
        clientVersions.fetch(CLONED_NAME);
        assertThat(clientVersions.policies().fetchAll().size(), is(1));
    }
}
