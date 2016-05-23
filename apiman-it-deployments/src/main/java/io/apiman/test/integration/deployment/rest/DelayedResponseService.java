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

package io.apiman.test.integration.deployment.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static io.apiman.test.integration.Suite.waitFor;

/**
 * Created by jsmolar.
 */
@Path("/response")
public class DelayedResponseService {

    @GET
    @Path("/delayedResponse/{delay}")
    public Response delayResponse(@PathParam("delay") int delay, @QueryParam("status") Integer status) {
        waitFor(delay, "Waiting %d milliseconds for delayed response from DelayedResponseService");

        if (status != null) {
            return Response.status(status).entity("Delayed response by " + delay
                + " milliseconds with status code " + status).build();
        } else {
            return Response.ok().entity("Delayed response by " + delay + " milliseconds").build();
        }
    }
}
