package com.example.board.post.model.response;

import com.example.board.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInfoResponse {
    private String title;
    private String content;
    private String userId;

    public PostInfoResponse(PostEntity postEntity) {
        this.title = postEntity.getTitle();
        this.content = postEntity.getContent();
        this.userId = postEntity.getUserId();
    }
}
