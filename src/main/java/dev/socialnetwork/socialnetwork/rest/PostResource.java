package dev.socialnetwork.socialnetwork.rest;

import dev.socialnetwork.socialnetwork.domain.model.Post;
import dev.socialnetwork.socialnetwork.domain.model.User;
import dev.socialnetwork.socialnetwork.domain.repository.FollowerRepository;
import dev.socialnetwork.socialnetwork.domain.repository.PostRepository;
import dev.socialnetwork.socialnetwork.domain.repository.UserRepository;
import dev.socialnetwork.socialnetwork.rest.dto.CreatePostRequest;
import dev.socialnetwork.socialnetwork.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import org.jboss.logging.annotations.Pos;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private UserRepository userRepository;

    private Validator validator;

    private PostRepository postRepository;

    private FollowerRepository followerRepository;

    @Inject
    public PostResource(UserRepository userRepository, Validator validator,PostRepository postRepository,
    FollowerRepository followerRepository ) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.postRepository = postRepository;
        this.followerRepository = followerRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest postRequest){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Post post = new Post();
        post.setText(postRequest.getText());
        post.setUser(user);
        postRepository.persist(post);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPost(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(followerId == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Nao foi passado o header param").build();
        }

        User follower = userRepository.findById(followerId);

        if(follower == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Follower nulo").build();
        }

        boolean follower1 = followerRepository.follower(follower, user);

        if(!follower1){
            return Response.status(Response.Status.FORBIDDEN).entity("So pode visualizar caso siga").build();
        }

        PanacheQuery<Post> query = postRepository.find("user",
                Sort.by("dateTime",Sort.Direction.Descending)
                ,user
        );
        var list = query.list();
        var postList = list.stream().map
                ( post -> PostResponse.fromEntity(post)).collect(Collectors.toList());
        return Response.ok(postList).build();
    }
}
