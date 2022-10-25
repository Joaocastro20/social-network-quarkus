package dev.socialnetwork.socialnetwork.rest;

import dev.socialnetwork.socialnetwork.domain.model.User;
import dev.socialnetwork.socialnetwork.rest.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @GET
    public Response listAllUsers(){
        PanacheQuery<PanacheEntityBase> query = User.findAll();
        return Response.ok(query.list()).build();
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        user.persist();
        return Response.ok(user).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletUser(@PathParam("id") Long id){
        User user = User.findById(id);
        if(user != null){
            user.delete();
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest){
        User user = User.findById(id);
        if(user != null){
            user.setName(userRequest.getName());
            user.setAge(userRequest.getAge());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
