package io.apiman.test.integration.deployment.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author opontes
 */
@Path("/url_rewriting")
public class URLRewritingService {
    @GET @Path("/get_data_to_rewrite")
    public Response getDataToRewrite(){
        return Response.ok()
                .header("URLRewrite","http://example.com/your/own/path")
                .entity("I want to change body message. http://example.com/your/own/path")
                .build();
    }
}
