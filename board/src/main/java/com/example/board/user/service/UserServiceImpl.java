package com.example.board.user.service;

import com.example.board.enums.ExceptionMessage;
import com.example.board.exception.InvalidException;
import com.example.board.user.converter.UserConverter;
import com.example.board.user.entity.UserEntity;
import com.example.board.user.model.request.LoginRequestDto;
import com.example.board.user.model.request.UserRequestDto;
import com.example.board.user.model.response.LoginResponseDto;
import com.example.board.user.model.response.UserResponseDto;
import com.example.board.user.repository.UserRepository;
import com.example.board.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String AT = "@";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        validateEmailAddress(userRequestDto.getEmail());
        validatePasswordLength(userRequestDto.getPassword());
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        UserEntity userEntity = UserConverter.to(userRequestDto);
        userRepository.save(userEntity);

        return UserConverter.from(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        validateEmailAddress(loginRequestDto.getEmail());
        validatePasswordLength(loginRequestDto.getPassword());

        if (userRepository.findUserEntityByEmail(loginRequestDto.getEmail()) == null) {
            // TODO: 커스텀 exception으로 수정
            throw new RuntimeException("가입자 정보가 없습니다.");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userRepository.findUserEntityByEmail(loginRequestDto.getEmail()).getPassword())) {
            // TODO: 커스텀 exception으로 수정
            throw new RuntimeException("가입자 정보가 없습니다.");
        }
        String accessToken = jwtTokenProvider.createAuthToken(loginRequestDto.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequestDto.getEmail());

        LoginResponseDto loginResponseDto = new LoginResponseDto();

        return loginResponseDto.builder().
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
