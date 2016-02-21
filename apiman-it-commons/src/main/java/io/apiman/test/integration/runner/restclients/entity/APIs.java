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

package io.apiman.test.integration.runner.restclients.entity;

import static io.apiman.test.integration.runner.RestAssuredUtils.withManager;

import io.apiman.test.integration.runner.restclients.AbstractEntityRestClient;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.NewApiBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.summary.ApiSummaryBean;

import java.util.Arrays;
import java.util.List;

/**
 * @author jcechace
 */
public class APIs extends AbstractEntityRestClient<ApiBean, NewApiBean> {

    public static final String RESOURCE_PATH = "/organizations/{0}/apis";

    public APIs(OrganizationBean org) {
        super(path(RESOURCE_PATH, org.getId()), ApiBean.class);
    }

    public List<ApiSummaryBean> fetchAll() {
        ApiSummaryBean[] services = withManager().get(getResourcePath()).as(ApiSummaryBean[].class);
        return Arrays.asList(services);
    }
}
