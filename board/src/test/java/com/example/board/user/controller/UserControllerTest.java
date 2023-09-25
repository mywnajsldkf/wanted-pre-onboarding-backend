package com.example.board.user.controller;

import com.example.board.user.model.request.UserRequestDto;
import com.example.board.user.model.response.UserResponseDto;
import com.example.board.user.service.UserServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @DisplayName("회원 가입 성공")
    @Test
    void signUpSuccess() throws Exception {
        // given
        /**
         * 회원가입 요청을 보내기 위해 SignUpRequest 객체 1개와 userService의 signUp에 대한 stub 생성
         * stub: 기존 코드를 스뮬레이션하거나 아직 개발되지 않은 코드를 임시로 대치하는 역할을 수행한다.
         */
        UserRequestDto request = userRequest();
        UserResponseDto response = userResponse();

        /**
         * UserRequestDto를 조작하기 위해 -> UserRequestDto 클래스 타입이라면 어떤 객체도 처리할 수 있다. any() 사용
         * any 사용할 때는 특정 클래스의 타입 지정할 것!
         */
        doReturn(response).when(userService)
                .createUser(any(UserRequestDto.class));

        // when
        /**
         * mockMVC에 데이터와 함께 POST 요청을 보낸다.
         * mockMvc의 perform에서, MockMvcRequestBuilders가 사용되어 요청 메서드 종류, 내용, 파라미터 등을 설정한다.
         * Gson: 객체가 아닌 문자열이어야하므로, 별도 변환을 위해 사용한다.
         */
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("email", response.getEmail()).exists())
                .andExpect(jsonPath("password", response.getPassword()).exists()).andReturn();
    }

    private UserRequestDto userRequest() {
        return UserRequestDto.builder()
                .email("test@test.test")
                .password("12341234")
                .build();
    }

    private UserResponseDto userResponse() {
        return UserResponseDto.builder()
                .email("test@test.test")
                .password("12341234")
                .build();
    }
}