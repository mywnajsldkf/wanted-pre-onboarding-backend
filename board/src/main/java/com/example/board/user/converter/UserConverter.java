package com.example.board.user.converter;

import com.example.board.user.entity.UserEntity;
import com.example.board.user.model.request.UserRequestDto;
import com.example.board.user.model.response.UserResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConverter {
    public static UserEntity to(UserRequestDto userRequestDto) {
        // TODO : userId 중복검사
        return UserEntity.builder().
                email(userRequestDto.getEmail()).
                password(userRequestDto.getPassword()).
                build();
    }

    public static UserResponseDto from(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserResponseDto.builder().
                email(userEntity.getEmail()).
                password(userEntity.getPassword()).
                build();
    }
}
