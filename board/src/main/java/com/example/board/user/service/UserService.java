package com.example.board.user.service;

import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.model.request.UserLoginRequest;
import com.example.board.user.model.response.TokenInfoResponse;
import com.example.board.user.model.response.UserInfoResponse;

public interface UserService {
    UserInfoResponse createUser(UserCreateRequest userCreateRequest);

    TokenInfoResponse loginUser(UserLoginRequest userLoginRequest);
}
