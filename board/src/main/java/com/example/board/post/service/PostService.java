package com.example.board.post.service;

import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.request.PostUpdateRequest;
import com.example.board.post.model.response.PostInfoResponse;
import org.springframework.data.domain.Page;

public interface PostService {
    PostInfoResponse createPost(String token, PostCreateRequest postCreateRequest);
    Page<PostInfoResponse> findPostPage(Integer page, Integer size);
    PostInfoResponse findPost(Long postId);
    PostInfoResponse updatePost(String token, Long postId, PostUpdateRequest postUpdateRequest);
    PostInfoResponse deletePost(String token, Long postId);
}
