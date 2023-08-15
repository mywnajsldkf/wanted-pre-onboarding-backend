package com.example.board.user.service;

import com.example.board.user.model.request.UserCreateRequest;

public interface UserService {
    void createUser(UserCreateRequest userCreateRequest);
}
