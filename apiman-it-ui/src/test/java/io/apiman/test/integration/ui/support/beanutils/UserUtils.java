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

import io.apiman.test.integration.runner.RestAssuredConfig;
import io.apiman.manager.api.beans.idm.UserBean;

/**
 * @author ldimaggi
 */
public class UserUtils {

    public static final String TEST_USER_NAME_BASE = "MyUsername";

    static {
        RestAssuredConfig.init();
    }

    /**
     * Create an instance of UserBean with unique name, fullname, email
     * This organization won't be created inside Apiman
     *
     * @return UserBean instance
     */
    public static UserBean local() {
        String username = BeanUtils.uniqueName(TEST_USER_NAME_BASE);
        UserBean user = new UserBean();
        user.setUsername(username);
        user.setFullName(username + " " + BeanUtils.uniqueName("Surname"));
        user.setEmail(username + "@example.com");
        return user;
    }
}
