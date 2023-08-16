package com.example.board.user.service;

import com.example.board.enums.ExceptionMessage;
import com.example.board.exception.InvalidException;
import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.repository.UserRepository;
import com.example.board.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Test
    @DisplayName("이메일에 '@'포함되어 있지 않으면, 예외를 던진다.")
    void throwExceptionIfEmailDoesNotContainAt() {
        // given
        String testEmail = "testtest.com";
        String testPassword = "qwer1234";

        UserServiceImpl testUserService = new UserServiceImpl(passwordEncoder, userRepository, jwtTokenProvider);
        UserCreateRequest testUserCreateRequest = new UserCreateRequest(testEmail, testPassword);

        // when & then
        assertThatThrownBy(() -> testUserService.createUser(testUserCreateRequest))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_EMAIL_ADDRESS.getMessage());
    }

    @Test
    @DisplayName("비밀번호가 8글자 미만이면, 예외를 던진다.")
    void throwExceptionIfPasswordIsUnder8() {
        // given
        String testEmail = "test@test.com";
        String testPassword = "qwer";

        UserServiceImpl testUserService = new UserServiceImpl(passwordEncoder, userRepository, jwtTokenProvider);
        UserCreateRequest testUserCreateRequest = new UserCreateRequest(testEmail, testPassword);

        // when & then
        assertThatThrownBy(() -> testUserService.createUser(testUserCreateRequest))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_PASSWORD_LENGTH.getMessage());
    }

}