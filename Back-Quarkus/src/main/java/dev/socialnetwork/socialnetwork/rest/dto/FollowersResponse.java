package dev.socialnetwork.socialnetwork.rest.dto;

import dev.socialnetwork.socialnetwork.domain.model.Followers;
import lombok.Data;

@Data
public class FollowersResponse {
    private Long id;
    private String name;

    private  Long followerId;

    public FollowersResponse() {
    }

    public FollowersResponse(Followers followers){
        this(followers.getId(), followers.getFollower().getName(),followers.getFollower().getId());
    }

    public FollowersResponse(Long id, String name,Long followerId) {
        this.id = id;
        this.name = name;
        this.followerId = followerId;
    }
}
