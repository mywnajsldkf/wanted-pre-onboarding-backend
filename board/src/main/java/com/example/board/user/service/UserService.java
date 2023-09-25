package com.example.board.user.service;

import com.example.board.user.model.request.LoginRequestDto;
import com.example.board.user.model.request.UserRequestDto;
import com.example.board.user.model.response.LoginResponseDto;
import com.example.board.user.model.response.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    LoginResponseDto loginUser(LoginRequestDto userLoginRequest);
}
