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

package io.apiman.test.integration.base.clients;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenManager;

import io.apiman.test.integration.base.AbstractClientTest;
import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.runner.annotations.misc.Contract;
import io.apiman.test.integration.runner.annotations.version.ApiVersion;
import io.apiman.test.integration.runner.annotations.version.ClientVersion;
import io.apiman.test.integration.runner.restclients.version.ClientVersions;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.clients.ClientVersionBean;
import io.apiman.manager.api.beans.summary.ContractSummaryBean;

import java.util.List;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author jkaspar
 */
@RunWith(ApimanRunner.class)
public class AbstractBreakContractTest extends AbstractClientTest {

    // Test Attributes

    protected static String CONTRACTS_PATH = "/organizations/{org}/clients/{client}/versions/{version}/contracts";
    protected static String CONTRACT_PATH = CONTRACTS_PATH + "/{id}";

    protected static ClientVersions clientVersions;

    protected static ResponseSpecification noExpectations;

    protected ContractSummaryBean unregisteredContractBean;
    protected ContractSummaryBean registeredContractBean;

    // Apiman Configuration

    @ApiVersion(api = "api", vPlans = {"planVersion"}, version = "2.0", unique = true)
    protected static ApiVersionBean secondApiVersion;

    @ClientVersion(
        version = "unregistered",
        unique = true,
        client = "client",
        publish = false,
        contracts = {
            @Contract(vPlan = "planVersion", vApi = "apiVersion"),
            @Contract(vPlan = "planVersion", vApi = "secondApiVersion")
        })
    protected ClientVersionBean unregisteredClientVersion;

    @ClientVersion(
        version = "registered",
        unique = true,
        client = "client",
        publish = true,
        contracts = {
            @Contract(vPlan = "planVersion", vApi = "apiVersion"),
            @Contract(vPlan = "planVersion", vApi = "secondApiVersion")
        })
    protected ClientVersionBean registeredClientVersion;

    @BeforeClass
    public static void setUp() {
        noExpectations = new ResponseSpecBuilder().build();
        clientVersions = new ClientVersions(client);
    }

    @Before
    public void setupContractBeans() {
        clientVersions.fetch(unregisteredClientVersion.getVersion());
        unregisteredContractBean = clientVersions.contracts()
            .fetch(organization, secondApiVersion, planVersion).getBean();

        clientVersions.fetch(registeredClientVersion.getVersion());
        registeredContractBean = clientVersions.contracts()
            .fetch(organization, secondApiVersion, planVersion).getBean();
    }

    /**
     * Send GET request for contract represented by ContractSummaryBean and require expectations from response
     * @param contract contract representation
     * @param expectations which will be required from response
     */
    protected static void getContract(ContractSummaryBean contract, ResponseSpecification expectations) {
        String orgId = contract.getClientOrganizationId();
        String clientId = contract.getClientId();
        String clientVersion = contract.getClientVersion();
        Long contractId = contract.getContractId();

        givenManager().
            get(CONTRACT_PATH,orgId, clientId, clientVersion, contractId).
        then().
            spec(expectations);
    }

    /**
     * Obtain all contract by given client version
     * @param clientVersion representation
     * @return List of all contracts maintaining by given app version
     */
    protected static List<ContractSummaryBean> getContracts(ClientVersionBean clientVersion) {
        clientVersions.fetch(clientVersion.getVersion());
        return clientVersions.contracts().fetchAll();
    }

    /**
     * Send DELETE request which break contract represented by ContractSummaryBean
     * and require expectations from response
     * @param contract contract representation
     * @param expectations which will be required from response
     */
    protected static void breakContract(ContractSummaryBean contract, ResponseSpecification expectations) {
        String orgId = contract.getClientOrganizationId();
        String clientId = contract.getClientId();
        String clientVersion = contract.getClientVersion();
        Long contractId = contract.getContractId();

        givenManager().
            delete(CONTRACT_PATH, orgId, clientId, clientVersions, contract).
        then().
            spec(expectations);
    }

    /**
     * Send DELETE request which break all contracts maintaining by given client version and require
     * expectations from response
     * @param clientVersion client version representation
     * @param expectations which will be required from response
     */
    protected static void breakAllContracts(ClientVersionBean clientVersion, ResponseSpecification expectations) {
        String org = clientVersion.getClient().getOrganization().getId();
        String client = clientVersion.getClient().getId();
        String version = clientVersion.getVersion();

        givenManager().
            delete(CONTRACTS_PATH, org, client, version).
        then().
            spec(expectations);
    }

    /**
     * Build response specification with expected status code
     * @param code expected status code
     * @return Response Specification
     */
    protected static ResponseSpecification expectedStatusCode(int code) {
        return new ResponseSpecBuilder().expectStatusCode(code).build();
    }
}
