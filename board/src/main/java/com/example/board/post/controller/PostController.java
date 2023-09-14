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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping
    public ResponseEntity<PostInfoResponse> createPost(@RequestHeader(name = "token") String token,
                                                       @RequestBody PostCreateRequest requestDto) {
        PostInfoResponse postInfoResponse = postService.createPost(token, requestDto);
        return ResponseEntity.created(URI.create("/api/post")).body(postInfoResponse);
    }

    @GetMapping
    public ResponseEntity<Page<PostInfoResponse>> findAllPost(@RequestParam(name = "page") int page,
                                                              @RequestParam(name = "size") int size) {
        Page<PostInfoResponse> pageResponse = postService.findPostPage(page, size);
        return ResponseEntity.ok().body(pageResponse);
    }

    @GetMapping("/detail")
    @ResponseBody
    public ResponseEntity<PostInfoResponse> findPost(@RequestParam(name = "postId") Long postId) {
        PostInfoResponse postInfoResponse = postService.findPost(postId);
        return ResponseEntity.ok().body(postInfoResponse);
    }

    @PatchMapping
    @ResponseBody
    public ResponseEntity<PostInfoResponse> updatePost(@RequestHeader(name = "token") String token,
                                                       @RequestParam(name = "postId") Long postId,
                                                       @RequestBody PostUpdateRequest postUpdateRequest) {
        PostInfoResponse postInfoResponse = postService.updatePost(token, postId, postUpdateRequest);
        return ResponseEntity.ok().body(postInfoResponse);
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<PostInfoResponse> deletePost(@RequestHeader(name = "token") String token,
                                                       @RequestParam(name = "postId") Long postId) {
        PostInfoResponse postInfoResponse = postService.deletePost(token, postId);
        return ResponseEntity.ok().body(postInfoResponse);
    }
}
