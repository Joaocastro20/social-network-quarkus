package dev.socialnetwork.socialnetwork.rest;

import dev.socialnetwork.socialnetwork.domain.model.Followers;
import dev.socialnetwork.socialnetwork.domain.repository.FollowerRepository;
import dev.socialnetwork.socialnetwork.domain.repository.UserRepository;
import dev.socialnetwork.socialnetwork.rest.dto.FollowerRequest;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowersResource {

    private FollowerRepository followerRepository;
    private UserRepository userRepository;

    public FollowersResource(FollowerRepository followerRepository, UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response saveFollower(@PathParam("userId") Long userId, FollowerRequest request){

        var user = userRepository.findById(userId);

        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = followerRepository.follower(follower, user);

        if(!follows){
            var entity = new Followers();
            entity.setUser(user);
            entity.setFollower(follower);
            followerRepository.persist(entity);
            return Response.status(Response.Status.NO_CONTENT).build();
        }


        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
