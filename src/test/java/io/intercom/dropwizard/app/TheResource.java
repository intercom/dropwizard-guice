package io.intercom.dropwizard.app;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Path("/overhead")
public class TheResource {

    @Inject
    private TheDependent theDependent;

    @GET
    public Response ok(@Context Request request) {
        return Response.ok("ok: " + getTheDependent().getA()).build();
    }

    public TheDependent getTheDependent() {
        return theDependent;
    }
}
