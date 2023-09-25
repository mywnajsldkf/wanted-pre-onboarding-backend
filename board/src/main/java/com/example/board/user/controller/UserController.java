package com.example.board.user.controller;

import com.example.board.user.model.request.LoginRequestDto;
import com.example.board.user.model.request.UserRequestDto;
import com.example.board.user.model.response.LoginResponseDto;
import com.example.board.user.model.response.UserResponseDto;
import com.example.board.user.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto userResponseDto = userService.createUser(requestDto);
        return ResponseEntity.created(URI.create("/api/user")).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = userService.loginUser(requestDto);
        Cookie cookie = new Cookie("token", loginResponseDto.getAccessToken());
        response.addCookie(cookie);
        return ResponseEntity.ok().body(loginResponseDto);
    }
}
