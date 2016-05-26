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

package io.apiman.test.integration.rest.entity;

import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.entity.Api;
import io.apiman.test.integration.runner.annotations.entity.Client;
import io.apiman.test.integration.runner.annotations.entity.Organization;
import io.apiman.test.integration.runner.annotations.entity.Plan;
import io.apiman.test.integration.runner.annotations.misc.ApiKey;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.misc.ManagedEndpoint;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.annotations.version.PlanVersion;
import io.apiman.test.integration.runner.restclients.entity.Organizations;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.PlanBean;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by jsmolar.
 *
 * Rules for organization entity deletion.
 * -cannot have any published APIs
 * -cannot have any registered client apps
 */
@RunWith(ApimanRunner.class)
public class DeleteOrg {

    //Empty Organization
    @Organization
    public static OrganizationBean org1;

    //Organization with registered client app
    @Organization
    public static OrganizationBean org2;

    @Api(organization = "org2")
    public static ApiBean api2;

    @ApiVersion(api = "api2", vPlans = {"plan"})
    @ManagedEndpoint
    private static String endpoint;

    @Plan(organization = "org2")
    @PlanVersion
    private static PlanBean plan;

    @Client(organization = "org2")
    private static ClientBean client;

    @ClientVersion(client = "client", contracts = @Contract(vPlan = "plan", vApi = "endpoint"))
    @ApiKey(vPlan = "plan", vApi = "endpoint")
    private static String apikey;

    //Organization with published API
    @Organization()
    public static OrganizationBean org3;

    @Api(organization = "org3")
    public static ApiBean api3;

    @ApiVersion(api = "api3")
    private static ApiVersionBean apiVersion3;

    private Organizations organizations;
    private ResponseSpecification specOK;
    private ResponseSpecification specConflict;

    @Before
    public void setUp() {
        organizations = new Organizations();
        specOK = new ResponseSpecBuilder().expectStatusCode(204).build();
        specConflict = new ResponseSpecBuilder().expectStatusCode(409).build();
    }

    @Test
    public void shouldDeleteEmptyOrganization() {
        organizations.delete(org1.getId(), specOK);
    }

    @Test
    public void shouldNotDeleteOrgWithRegisteredClientApp() {
        organizations.delete(org2.getId(), specConflict);
    }

    @Test
    public void shouldNotDeleteOrgWithPublishedApi() {
        organizations.delete(org3.getId(), specConflict);
    }

}
