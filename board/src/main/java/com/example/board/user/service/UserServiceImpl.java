package com.example.board.user.service;

import com.example.board.enums.ExceptionMessage;
import com.example.board.exception.InvalidException;
import com.example.board.user.converter.UserConverter;
import com.example.board.user.entity.UserEntity;
import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.model.request.UserLoginRequest;
import com.example.board.user.model.response.TokenInfoResponse;
import com.example.board.user.model.response.UserInfoResponse;
import com.example.board.user.repository.UserRepository;
import com.example.board.util.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserInfoResponse createUser(UserCreateRequest userCreateRequest) {
        validateEmailAddress(userCreateRequest.getEmail());
        validatePasswordLength(userCreateRequest.getPassword());
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        UserEntity userEntity = UserConverter.to(userCreateRequest);
        userRepository.save(userEntity);

        return UserConverter.from(userEntity);
    }

    @Override
    public TokenInfoResponse loginUser(UserLoginRequest userLoginRequest) {
        validateEmailAddress(userLoginRequest.getEmail());
        validatePasswordLength(userLoginRequest.getPassword());
        TokenInfoResponse tokenInfoResponse = new TokenInfoResponse();
        if (userRepository.findUserEntityByEmail(userLoginRequest.getEmail()) == null) {
            // TODO: 커스텀 exception으로 수정
            throw new RuntimeException("가입자 정보가 없습니다.");
        }

        if (!passwordEncoder.matches(userLoginRequest.getPassword(), userRepository.findUserEntityByEmail(userLoginRequest.getEmail()).getPassword())) {
            // TODO: 커스텀 exception으로 수정
            throw new RuntimeException("가입자 정보가 없습니다.");
        }
        String accessToken = jwtTokenProvider.createAuthToken(userLoginRequest.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginRequest.getEmail());
        return tokenInfoResponse.builder().
                accessToken(accessToken).
                refreshToken(refreshToken).
                build();
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
