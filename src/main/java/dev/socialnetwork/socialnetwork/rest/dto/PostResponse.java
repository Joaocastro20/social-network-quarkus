package dev.socialnetwork.socialnetwork.rest.dto;

import dev.socialnetwork.socialnetwork.domain.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime date;

    public static PostResponse fromEntity(Post post){
        var response = new PostResponse();
        response.setText(post.getText());
        response.setDate(post.getDate());
        return response;
    }
}
