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

package io.apiman.test.integration.rest.policies.transferquota.upload;

import io.apiman.test.integration.rest.policies.transferquota.AbstractTransferQuotaPolicyIT;

import com.jayway.restassured.response.Response;
import org.junit.Test;

/**
 * @author jkaspar
 */
public abstract class AbstractTransferQuotaUploadPolicyIT extends AbstractTransferQuotaPolicyIT {

    @Override
    protected Response useUpNBytes(int n) {
        return uploadNBytes(n);
    }

    @Test
    public void downloadDoNotUseUpQuota() {
        downloadNBytes(1);
        assertRemaining(LIMIT);
    }
}
