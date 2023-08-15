package com.example.board.user.service;

import com.example.board.enums.ExceptionMessage;
import com.example.board.exception.InvalidException;
import com.example.board.user.model.request.UserCreateRequest;
import com.example.board.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이메일에 '@'포함되어 있지 않으면, 예외를 던진다.")
    void ifEmailIsNotContainAtThrowException() {
        // given
        String testEmail = "testtest.com";
        String testPassword = "qwer1234";

        UserServiceImpl testUserService = new UserServiceImpl(userRepository);
        UserCreateRequest testUserCreateRequest = new UserCreateRequest(testEmail, testPassword);

        // when & then
        assertThatThrownBy(() -> testUserService.createUser(testUserCreateRequest))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_EMAIL_ADDRESS.getMessage());
    }
}