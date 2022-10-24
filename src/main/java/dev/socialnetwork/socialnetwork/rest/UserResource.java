package dev.socialnetwork.socialnetwork.rest;

import dev.socialnetwork.socialnetwork.rest.dto.CreateUserRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @GET
    public Response listAllUsers(){
        return Response.ok().build();
    }

    @POST
    public Response createUser(CreateUserRequest userRequest){
        return Response.ok(userRequest).build();
    }

}
