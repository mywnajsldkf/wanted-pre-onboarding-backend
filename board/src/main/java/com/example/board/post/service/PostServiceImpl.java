package com.example.board.post.service;

import com.example.board.post.converter.PostConverter;
import com.example.board.post.entity.PostEntity;
import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.response.PostInfoResponse;
import com.example.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public Page<PostInfoResponse> findAllPost(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PostEntity> postEntities = postRepository.findAll(pageRequest);
        Page<PostInfoResponse> postInfoResponses = postEntities.map(postEntity -> new PostInfoResponse(postEntity));
        return postInfoResponses;
     }

}
