package com.example.board.user.service;

import com.example.board.user.model.request.UserLoginRequest;
import com.example.board.user.model.request.UserRequestDto;
import com.example.board.user.model.response.TokenInfoResponse;
import com.example.board.user.model.response.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    TokenInfoResponse loginUser(UserLoginRequest userLoginRequest);
}
