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
import io.apiman.test.integration.runner.restclients.entity.Organizations;
import io.apiman.manager.api.beans.orgs.NewOrganizationBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;

/**
 * @author jcechace
 */
public class OrgUtils {

    public static final String TEST_ORG_NAME_BASE = "MyTestOrg";

    static {
        RestAssuredConfig.init();
    }

    /**
     * Create an instance of OrganizationBean with unique name and description
     * This organization won't be created inside Apiman
     *
     * @return OrganizationBean instance
     */
    public static OrganizationBean local() {
        OrganizationBean org = new OrganizationBean();
        org.setName(uniqueName(TEST_ORG_NAME_BASE));
        org.setDescription(String.format("Description of %s", org.getName()));
        return org;
    }

    /**
     * Create an instance of OrganizationBean with unique name and description
     * This organization will be also create inside Apiman
     *
     * @return OrganizationBean instance
     */
    public static OrganizationBean remote() {
        NewOrganizationBean org = new NewOrganizationBean();
        org.setName(uniqueName(TEST_ORG_NAME_BASE));
        org.setDescription(String.format("Description of %s", org.getName()));

        Organizations client = new Organizations();
        return client.create(org).getBean();
    }
}
