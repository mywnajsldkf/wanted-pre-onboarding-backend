package com.example.board.post.service;

import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.response.PostInfoResponse;

public interface PostService {
    PostInfoResponse createPost(PostCreateRequest postCreateRequest);
}
