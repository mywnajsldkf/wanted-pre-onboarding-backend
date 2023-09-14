package com.example.board.post.converter;

import com.example.board.post.entity.PostEntity;
import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.response.PostInfoResponse;

public class PostConverter {
    public static PostEntity to(PostCreateRequest postCreateRequest) {
        return PostEntity.builder().
                title(postCreateRequest.getTitle()).
                content(postCreateRequest.getContent()).
                userId(postCreateRequest.getUserId()).
                build();
    }

    public static PostInfoResponse from(PostEntity postEntity) {
        if (postEntity == null) {
            return null;
        }

        return PostInfoResponse.builder().
                title(postEntity.getTitle()).
                content(postEntity.getContent()).
                userId(postEntity.getUserId()).
                build();
    }
}
