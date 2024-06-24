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

import io.apiman.test.integration.base.entity.TestData;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author opontes
 */
@Path("/transform")
public class TransformationService {

    @GET @Path("/json") @Produces(MediaType.APPLICATION_JSON)
    public TestData getJSON(){
        return new TestData();
    }

    @POST @Path("/json") @Consumes(MediaType.APPLICATION_JSON)
    public Response postJSON(TestData testData){
        return Response.status((testData.equals(new TestData())) ? 200 : 500).build();
    }

    @GET @Path("/xml") @Produces(MediaType.APPLICATION_XML)
    public TestData getXML(){
        return new TestData();
    }

    @POST @Path("/xml") @Consumes(MediaType.APPLICATION_XML)
    public Response postXML(TestData testData){
        return Response.status((testData.equals(new TestData())) ? 200 : 500).build();
    }
}
