package com.example.board.post.service;

import com.example.board.post.converter.PostConverter;
import com.example.board.post.entity.PostEntity;
import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.response.PostInfoResponse;
import com.example.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Override
    public PostInfoResponse createPost(PostCreateRequest postCreateRequest) {
        PostEntity postEntity = PostConverter.to(postCreateRequest);
        postRepository.save(postEntity);
        return PostConverter.from(postEntity);
    }
}
