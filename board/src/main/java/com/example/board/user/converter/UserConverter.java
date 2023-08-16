package com.example.board.user.converter;

import com.example.board.user.entity.UserEntity;
import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.model.response.UserInfoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConverter {
    public static UserEntity to(UserCreateRequest userCreateRequest) {
        return UserEntity.builder().
                email(userCreateRequest.getEmail()).
                password(userCreateRequest.getPassword()).
                build();
    }

    public static UserInfoResponse from(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserInfoResponse.builder().
                email(userEntity.getEmail()).
                password(userEntity.getPassword()).
                build();
    }
}
