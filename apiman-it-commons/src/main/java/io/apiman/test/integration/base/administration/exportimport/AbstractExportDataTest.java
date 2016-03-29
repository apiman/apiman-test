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

package io.apiman.test.integration.base.administration.exportimport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.valid4j.matchers.jsonpath.JsonPathMatchers.hasJsonPath;
import static org.valid4j.matchers.jsonpath.JsonPathMatchers.isJson;

import io.apiman.test.integration.base.AbstractClientTest;
import io.apiman.test.integration.runner.annotations.entity.Plugin;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.manager.api.beans.clients.ClientVersionBean;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * Test create following resources and assert presence of the resources in exported json:
 *    Organization,
 *    Api + version,
 *    Plan + version,
 *    Client + version (with policy and contract),
 *    Plugin
 *
 * @author jkaspar
 */
@Plugin(artifactId = "apiman-plugins-jsonp-policy")
public abstract class AbstractExportDataTest extends AbstractClientTest {

    @ClientVersion(client = "client", policies = @Policies("arbitrary"),
            contracts = @Contract(vApi = "apiVersion", vPlan = "planVersion"))
    protected static ClientVersionBean clientVersion;

    protected abstract String getExportedJson() throws InterruptedException, IOException;

    protected static String exportedJson;

    @Before
    public void setUp() throws InterruptedException, IOException {
        if (exportedJson == null) {
            exportedJson = getExportedJson();
        }
    }

    @Test
    public void shouldBeValidJson() {
        assertThat("Exported file is not valid JSON", exportedJson, isJson());
    }

    @Test
    public void shouldContainOnePlugin() {
        assertThat(exportedJson, hasJsonPath("$.Plugins", hasSize(1)));
    }

    @Test
    public void shouldContainOnePolicyOnClientVersion() {
        assertThat(exportedJson, hasJsonPath("$..Clients..Policies", hasSize(1)));
    }

    @Test
    public void shouldContainOneContractOnClientVersion() {
        assertThat(exportedJson, hasJsonPath("$..Clients..Contracts", hasSize(1)));
    }

    @Test
    public void shouldContainOrganization() {
        assertThat(exportedJson, hasJsonPath("$..OrganizationBean.id", contains(organization.getId())));
    }

    @Test
    public void shouldContainApiShould() {
        assertThat(exportedJson, hasJsonPath("$..ApiBean.id", contains(api.getId())));
    }

    @Test
    public void shouldContainApiVersion() {
        assertThat(exportedJson, hasJsonPath("$..ApiVersionBean.version", contains(apiVersion.getVersion())));
    }

    @Test
    public void shouldContainPlan() {
        assertThat(exportedJson, hasJsonPath("$..PlanBean.id", contains(plan.getId())));
    }

    @Test
    public void shouldContainPlanVersion() {
        assertThat(exportedJson, hasJsonPath("$..PlanVersionBean.version", contains(planVersion.getVersion())));
    }

    @Test
    public void shouldContainClient() {
        assertThat(exportedJson, hasJsonPath("$..ClientBean.id", contains(client.getId())));
    }

    @Test
    public void shouldContainClientVersion() {
        assertThat(exportedJson, hasJsonPath("$..ClientVersionBean.version", contains(clientVersion.getVersion())));
    }

}
