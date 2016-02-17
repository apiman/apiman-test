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

package io.apiman.test.integration.ui.support.beanutils;

import static io.apiman.test.integration.ui.support.beanutils.BeanUtils.uniqueName;

import io.apiman.test.integration.runner.RestAssuredConfig;
import io.apiman.manager.api.beans.clients.ClientBean;

/**
 * @author ldimaggi
 */
public class ClientUtils {

    public static final String TEST_CLIENT_NAME_BASE = "MyTestClient";

    static {
        RestAssuredConfig.init();
    }

    /**
     * Create an instance of ClientBean with unique name and description
     * This ClientBean won't be created inside Apiman
     *
     * @return ClientBean instance
     */
    public static ClientBean local() {
        ClientBean client = new ClientBean();
        client.setName(uniqueName(TEST_CLIENT_NAME_BASE));
        client.setDescription(String.format("Description of %s", client.getName()));
        return client;
    }

}
