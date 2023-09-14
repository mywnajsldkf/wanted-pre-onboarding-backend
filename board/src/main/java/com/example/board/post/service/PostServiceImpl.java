package com.example.board.post.service;

import com.example.board.post.converter.PostConverter;
import com.example.board.post.entity.PostEntity;
import com.example.board.post.model.request.PostCreateRequest;
import com.example.board.post.model.request.PostUpdateRequest;
import com.example.board.post.model.response.PostInfoResponse;
import com.example.board.post.repository.PostRepository;
import com.example.board.user.repository.UserRepository;
import com.example.board.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public PostInfoResponse createPost(String token, PostCreateRequest postCreateRequest) {
        String email = jwtTokenProvider.parseJwtToken(token);
        if (userRepository.findUserEntityByEmail(email) == null) {
            throw new RuntimeException("권한이 없는 계정입니다.");
        }
        PostEntity postEntity = PostConverter.to(postCreateRequest);
        postEntity.setUserId(email);
        postRepository.save(postEntity);
        return PostConverter.from(postEntity);
    }

    @Override
    public Page<PostInfoResponse> findPostPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PostEntity> postEntities = postRepository.findAll(pageRequest);
        Page<PostInfoResponse> postInfoResponses = postEntities.map(postEntity -> new PostInfoResponse(postEntity));
        return postInfoResponses;
     }

    @Override
    public PostInfoResponse findPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).get();
        return PostConverter.from(postEntity);
    }

    @Override
    public PostInfoResponse updatePost(String token, Long postId, PostUpdateRequest postUpdateRequest) {
        String email = jwtTokenProvider.parseJwtToken(token);
        if (userRepository.findUserEntityByEmail(email) == null) {
            throw new RuntimeException("권한이 없는 계정입니다.");
        }
        
        PostEntity postEntity = postRepository.findById(postId).get();
        if (postEntity.getTitle() != null) {
            postEntity.setTitle(postUpdateRequest.getTitle());
        }
        if  (postEntity.getContent().equals(null)) {
            postEntity.setContent(postUpdateRequest.getContent());
        }
        postEntity.setUserId(email);
        postRepository.save(postEntity);
        return PostConverter.from(postRepository.findById(postId).get());
    }

    @Override
    public PostInfoResponse deletePost(String token, Long postId) {
        String email = jwtTokenProvider.parseJwtToken(token);
        if (userRepository.findUserEntityByEmail(email) == null) {
            throw new RuntimeException("권한이 없는 계정입니다.");
        }
        PostEntity postEntity = postRepository.findById(postId).get();
        postRepository.deleteById(postId);
        return PostConverter.from(postEntity);
    }


}
