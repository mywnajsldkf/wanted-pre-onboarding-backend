package com.example.board.post.controller;

import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.response.PostInfoResponse;
import com.example.board.post.service.PostServiceImpl;
import com.example.board.support.ApiResponse;
import com.example.board.support.ApiResponseGenerator;
import com.example.board.support.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping
    @ResponseBody
    public ApiResponse<ApiResponse.SuccessBody<PostInfoResponse>> createPost(@RequestBody PostCreateRequest request) {
        return ApiResponseGenerator.success(postService.createPost(request), HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
    }
}
