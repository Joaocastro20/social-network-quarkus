package dev.socialnetwork.socialnetwork.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePostRequest {
    @NotBlank
    private String text;
}
