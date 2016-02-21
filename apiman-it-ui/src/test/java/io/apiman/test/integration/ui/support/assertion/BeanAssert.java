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

package io.apiman.test.integration.ui.support.assertion;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenManager;

import static org.hamcrest.Matchers.equalTo;

import io.apiman.test.integration.runner.restclients.VersionRestClient;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;
import io.apiman.manager.api.beans.summary.PolicySummaryBean;

import java.util.List;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

/**
 * @author jcechace
 */
public class BeanAssert {

    /**
     * Asserts that the given bean matches one inside apiman.
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, description
     *
     * @param expected expected bean
     */
    public static void assertOrganization(OrganizationBean expected) {
        final String path = "/organizations/{org}";
        givenManager().
            get(path, expected.getName()).
        then().
            body("name", equalTo(expected.getName())).
            body("description", equalTo(expected.getDescription()));
    }

    /**
     * Asserts that the given bean matches one inside apiman.
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, description
     *
     * @param expected expected bean
     */
    public static void assertApi(ApiBean expected) {
        final String path = "/organizations/{org}/apis/{api}";
        givenManager().
            get(path, expected.getOrganization().getName(), expected.getName()).
        then().
            statusCode(200).
            body("name", equalTo(expected.getName())).
            body("description", equalTo(expected.getDescription()));
    }

    /**
     * Asserts that the given bean matches one inside apiman.
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: version
     *
     * @param expected expected bean
     */
    public static void assertApiVersion(ApiVersionBean expected) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectBody("version", equalTo(expected.getVersion()));
        assertApiVersion(expected, builder.build());
    }

    /**
     * Assert that the given bean have representation inside apiman.
     * Send GET request for that bean and apply expectations on response.
     *
     * @param apiVersion used for find the apiman bean using version and api attributes from this bean
     * @param expectations expectations applied on apiman's response
     */
    public static void assertApiVersion(ApiVersionBean apiVersion, ResponseSpecification expectations) {
        final String path = "/organizations/{org}/apis/{api}/versions/{version}";

        String org = apiVersion.getApi().getOrganization().getName();
        String name = apiVersion.getApi().getName();
        String version = apiVersion.getVersion();

        givenManager().
            get(path, org, name, version).
        then().
            statusCode(200).
            spec(expectations);
    }

    /**
     * Asserts that the given bean matches one inside apiman.
     * This method does not have to cross check all fields.
     *
     *  Currently checked fields: name, description
     *
     * @param expected expected bean
     */
    public static void assertPolicyDefinition(PolicyDefinitionBean expected) {
        final String path = "/policyDefs/{def}";

        givenManager().
            get(path, expected.getId()).
        then().
            body("id", equalTo(expected.getId())).
            body("name", equalTo(expected.getName()));
    }

    public static void assertPolicyPresent(VersionRestClient<?> client, String policyDefId) {
        boolean containsType = client.policies().containsType(policyDefId);
        Assert.assertTrue(String.format("Expected a policy of type %s to be present", policyDefId), containsType);
    }

    public static void assertNoPolicies(VersionRestClient<?> client) {
        List<PolicySummaryBean> policies = client.policies().fetchAll();
        Assert.assertThat("Expected no policies to be present", policies, Matchers.empty());
    }
}
