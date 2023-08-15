package com.example.board.post.service;

import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.response.PostInfoResponse;
import org.springframework.data.domain.Page;

public interface PostService {
    PostInfoResponse createPost(PostCreateRequest postCreateRequest);
    Page<PostInfoResponse> findAllPost(Integer page, Integer size);
    PostInfoResponse findPost(Long postId);
}
