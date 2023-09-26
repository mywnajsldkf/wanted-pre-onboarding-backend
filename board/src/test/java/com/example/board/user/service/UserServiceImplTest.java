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
    void throwExceptionIfEmailDoesNotContainAt() {
        // given
        String testEmail = "testtest.com";
        String testPassword = "qwer1234";

        @Nested
        @DisplayName("이메일에 @가 없으면")
        class Context_without_at {
            // given
            UserRequestDto request = userRequest("testtest.test", "12341234");

            @Test
            @DisplayName("오류 메시지(유효하지 않는 이메일 형식)를 던진다.")
            /**
             * 질문: 예외 테스트는 목 객체를 생성할 필요없으니까?
             */
            void it_returns_exception_invalid_email_address() {
                // when & then
                assertEquals(ExceptionMessage.INVALID_EMAIL_ADDRESS.getMessage(),
                        assertThrows(InvalidException.class, () -> {
                            userService.createUser(request);
                        }).getMessage());
            }
        }

        @Nested
        @DisplayName("비밀번호 길이가 8글자 미만이면")
        class Password_length_under_8 {
            // given
            UserRequestDto request = userRequest("test@test.test", "1234");

            @Test
            @DisplayName("오류 메시지(비밀번호 길이가 8글자 미만)을 던진다.")
            void it_returns_exception_invalid_password_length() {
                // when & then
                assertEquals(ExceptionMessage.INVALID_PASSWORD_LENGTH.getMessage(),
                        assertThrows(InvalidException.class, () -> {
                            userService.createUser(request);
                        }).getMessage());
            }
        }
    }

    private UserRequestDto userRequest(String testEmail, String testPassword) {
        return UserRequestDto.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
    }
}