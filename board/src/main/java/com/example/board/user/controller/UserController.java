package com.example.board.user.controller;

import com.example.board.support.ApiResponse;
import com.example.board.support.ApiResponseGenerator;
import com.example.board.support.MessageCode;
import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.model.request.UserLoginRequest;
import com.example.board.user.model.response.TokenInfoResponse;
import com.example.board.user.model.response.UserInfoResponse;
import com.example.board.user.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping()
    @ResponseBody
    public ApiResponse<ApiResponse.SuccessBody<UserInfoResponse>> createUser(@RequestBody UserCreateRequest request) {
        return ApiResponseGenerator.success(userService.createUser(request), HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResponse<ApiResponse.SuccessBody<TokenInfoResponse>> loginUser(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        TokenInfoResponse tokenInfoResponse = userService.loginUser(request);
        Cookie cookie = new Cookie("token", tokenInfoResponse.getAccessToken());
        response.addCookie(cookie);
        return ApiResponseGenerator.success(tokenInfoResponse, HttpStatus.OK, MessageCode.SUCCESS);
    }
}
