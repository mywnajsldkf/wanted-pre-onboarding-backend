package com.example.board.user.service;

import com.example.board.enums.ExceptionMessage;
import com.example.board.exception.InvalidException;
import com.example.board.user.converter.UserConverter;
import com.example.board.user.entity.UserEntity;
import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String AT = "@";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final UserRepository userRepository;

    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        validateEmailAddress(userCreateRequest.getEmail());
        validatePasswordLength(userCreateRequest.getPassword());
        UserEntity userEntity = UserConverter.to(userCreateRequest);
        userRepository.save(userEntity);
    }

    private void validateEmailAddress(String email) {
        if (!email.matches(AT)) {
            throw new InvalidException(ExceptionMessage.INVALID_EMAIL_ADDRESS.getMessage());
        }
    }

    private void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidException(ExceptionMessage.INVALID_PASSWORD_LENGTH.getMessage());
        }
    }
}
