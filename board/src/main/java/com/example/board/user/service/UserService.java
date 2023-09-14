package com.example.board.user.service;

import com.example.board.user.model.request.LoginRequestDto;
import com.example.board.user.model.request.UserRequestDto;
import com.example.board.user.model.response.TokenResponseDto;
import com.example.board.user.model.response.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    TokenResponseDto loginUser(LoginRequestDto userLoginRequest);
}
