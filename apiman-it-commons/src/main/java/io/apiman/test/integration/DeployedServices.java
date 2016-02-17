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

package io.apiman.test.integration;

/**
 * Contains list of endpoints which are deployed from apiman-deployment-service
 * @author jkaspar
 */
public class DeployedServices {

    public static final String ROOT = "/qa";
    public static final String REST_ROOT = ROOT + "/rest";

    public static final String ECHO_ENDPOINT = ROOT + "/echo";
    public static final String DOWNLOAD_ENDPOINT = ROOT + "/download";
    public static final String STATUS_CODE_ENDPOINT = REST_ROOT + "/status";

}
