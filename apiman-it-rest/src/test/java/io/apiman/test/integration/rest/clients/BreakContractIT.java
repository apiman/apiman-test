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

package io.apiman.test.integration.rest.clients;

import static org.junit.Assert.assertEquals;

import io.apiman.test.integration.base.clients.AbstractBreakContractTest;
import io.apiman.test.integration.categories.SmokeTest;
import io.apiman.manager.api.beans.summary.ContractSummaryBean;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author jkaspar
 */
@Category({SmokeTest.class})
public class BreakContractIT extends AbstractBreakContractTest {

    @Test
    public void canBreakContract() throws Exception {
        breakContract(unregisteredContractBean, expectedStatusCode(204));
        getContract(unregisteredContractBean, expectedStatusCode(404));
    }

    @Test
    public void canBreakAllContracts() throws Exception {
        breakAllContracts(unregisteredClientVersion, expectedStatusCode(204));

        List<ContractSummaryBean> contracts = getContracts(unregisteredClientVersion);
        assertEquals("Unexpected number of contracts", 0, contracts.size());
    }

    // TODO: Should we remove this test?
    @Test
    @Ignore
    public void canNotBreakContractOnRegisteredClientVersion() throws Exception {
        breakContract(registeredContractBean, noExpectations);
        getContract(registeredContractBean, expectedStatusCode(200));
    }

    // TODO: Should we remove this test?
    @Test
    @Ignore
    public void canNotBreakAllContractsOnRegisteredClientVersion() throws Exception {
        List<ContractSummaryBean> contractsBefore = getContracts(registeredClientVersion);
        breakAllContracts(registeredClientVersion, noExpectations);
        List<ContractSummaryBean> contractsAfter = getContracts(registeredClientVersion);

        assertEquals("Unexpected number of contracts", contractsBefore.size(), contractsAfter.size());
    }
}
