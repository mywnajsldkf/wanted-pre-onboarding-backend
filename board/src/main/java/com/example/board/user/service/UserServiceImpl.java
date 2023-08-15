package com.example.board.user.service;

import com.example.board.enums.ExceptionMessage;
import com.example.board.exception.InvalidException;
import com.example.board.user.converter.UserConverter;
import com.example.board.user.entity.UserEntity;
import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.model.response.UserInfoResponse;
import com.example.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String AT = "@";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserInfoResponse createUser(UserCreateRequest userCreateRequest) {
        validateEmailAddress(userCreateRequest.getEmail());
        validatePasswordLength(userCreateRequest.getPassword());
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        UserEntity userEntity = UserConverter.to(userCreateRequest);
        userRepository.save(userEntity);

        return UserConverter.from(userEntity);
    }

    private void validateEmailAddress(String email) {
        if (!email.contains(AT)) {
            throw new InvalidException(ExceptionMessage.INVALID_EMAIL_ADDRESS.getMessage());
        }
    }

    private void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidException(ExceptionMessage.INVALID_PASSWORD_LENGTH.getMessage());
        }
    }
}
