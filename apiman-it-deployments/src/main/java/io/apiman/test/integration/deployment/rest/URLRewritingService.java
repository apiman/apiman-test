package io.apiman.test.integration.deployment.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author opontes
 */
@Path("/URLRewriting")
public class URLRewritingService {
    @GET @Path("/getDataToRewrite")
    public Response getDataToRewrite(){
        return Response.ok()
                .header("URLRewrite","http://localhost:8080/apimanui/api-manager/orgs/TestOrg1455697754852599/apis/" +
                        "TestApi14556977574004763/1.0/policies/362")
                .header("Change", "I want to change")
                .entity("I want to change ---- body message. http://localhost:8080/apimanui/api-manager/orgs/" +
                        "TestOrg1455697754852599/apis/TestApi14556977574004763/1.0/policies/362")
                .build();
    }
}
