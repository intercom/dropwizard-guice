package io.intercom.dropwizard.jersey2;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import io.intercom.dropwizard.app.TheDependent;

@Path("/overhead")
public class Jersey2Resource {

    /*
    We don't try and bridge h2k with guice, thus this Resource won't work with when we ask
    guice to provide it
     */
    @Inject
    private Request request;

    @GET
    public Response ok() {
        return Response.ok("ok").build();
    }

}
