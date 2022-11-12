package dev.socialnetwork.socialnetwork.rest;

import dev.socialnetwork.socialnetwork.domain.model.Followers;
import dev.socialnetwork.socialnetwork.domain.model.User;
import dev.socialnetwork.socialnetwork.domain.repository.FollowerRepository;
import dev.socialnetwork.socialnetwork.domain.repository.UserRepository;
import dev.socialnetwork.socialnetwork.rest.dto.FollowerRequest;
import dev.socialnetwork.socialnetwork.rest.dto.FollowersResponse;
import dev.socialnetwork.socialnetwork.rest.dto.FollowersUserResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

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

        if(userId.equals(follower.getId())){
            return Response.status(Response.Status.CONFLICT).entity("Nao é possivel seguir a si mesmo").build();
        }

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

    @GET
    @Transactional
    public Response findFollowersByUser(@PathParam("userId") Long userId){
        List<Followers> query = followerRepository.findByUser(userId);

        FollowersUserResponse followersUserResponse = new FollowersUserResponse();
        followersUserResponse.setFollowersCount(query.size());

        var followersList =
                query.stream().map(FollowersResponse::new).collect(Collectors.toList());
        followersUserResponse.setContent(followersList);

        return Response.ok(followersUserResponse).build();
    }

    @DELETE
    @Transactional
    public Response unfollowUser(
            @PathParam("userId") Long userId,
            @QueryParam("followerId") Long followerId){
        var user = userRepository.findById(userId);

        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        followerRepository.deleteByFollowerAndUser(followerId,userId);

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
