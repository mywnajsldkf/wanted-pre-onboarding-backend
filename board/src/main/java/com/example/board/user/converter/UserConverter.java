package com.example.board.user.converter;

import com.example.board.user.entity.UserEntity;
import com.example.board.user.model.request.UserCreateRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConverter {
    public static UserEntity to(UserCreateRequest userCreateRequest) {
        // TODO : userId 중복검사
        return UserEntity.builder().
                email(userCreateRequest.getEmail()).
                password(userCreateRequest.getPassword()).
                build();
    }

}
