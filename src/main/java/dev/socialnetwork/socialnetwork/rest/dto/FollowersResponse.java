package dev.socialnetwork.socialnetwork.rest.dto;

import dev.socialnetwork.socialnetwork.domain.model.Followers;
import lombok.Data;

@Data
public class FollowersResponse {
    private Long id;
    private String name;

    public FollowersResponse() {
    }

    public FollowersResponse(Followers followers){
        this(followers.getId(), followers.getFollower().getName());
    }

    public FollowersResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
