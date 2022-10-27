package dev.socialnetwork.socialnetwork.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowersUserResponse {
    private Integer followersCount;
    private List<FollowersResponse> content;
}
