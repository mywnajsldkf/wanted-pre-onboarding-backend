package com.example.board.post.controller;

import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.request.PostUpdateRequest;
import com.example.board.post.model.response.PostInfoResponse;
import com.example.board.post.service.PostServiceImpl;
import com.example.board.support.ApiResponse;
import com.example.board.support.ApiResponseGenerator;
import com.example.board.support.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping
    @ResponseBody
    public ApiResponse<ApiResponse.SuccessBody<Page<PostInfoResponse>>> findAllPost(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        return ApiResponseGenerator.success(postService.findAllPost(page, size), HttpStatus.OK, MessageCode.SUCCESS);
    }

    @GetMapping("/detail")
    @ResponseBody
    public ApiResponse<ApiResponse.SuccessBody<PostInfoResponse>> findPost(@RequestParam(name = "postId") Long postId) {
        return ApiResponseGenerator.success(postService.findPost(postId), HttpStatus.OK, MessageCode.SUCCESS);
    }

    @PatchMapping()
    @ResponseBody
    public ApiResponse<ApiResponse.SuccessBody<PostInfoResponse>> updatePost(@RequestHeader(name = "token") String token,
                                                                             @RequestParam(name = "postId") Long postId,
                                                                             @RequestBody PostUpdateRequest postUpdateRequest) {
        return ApiResponseGenerator.success(postService.updatePost(token, postId, postUpdateRequest), HttpStatus.OK, MessageCode.SUCCESS);
    }

    @DeleteMapping()
    @ResponseBody
    public ApiResponse<ApiResponse.SuccessBody<PostInfoResponse>> deletePost(@RequestHeader(name = "token") String token,
                                                                             @RequestParam(name = "postId") Long postId) {
        return ApiResponseGenerator.success(postService.deletePost(token, postId), HttpStatus.OK, MessageCode.RESOURCE_DELETED);
    }
}
