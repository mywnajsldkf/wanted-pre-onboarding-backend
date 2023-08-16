package com.example.board.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {
    INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, "이메일에 '@'가 없습니다."),
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "비밀번호 길이가 8글자 미만입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    USERINFO_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "가입되지 않은 사용자입니다."),
    PASSWORD_IS_NOT_CORRECT(HttpStatus.BAD_REQUEST, "사용자 비밀번호가 일치하지 않습니다."),
    ACCOUNT_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "권한이 없는 계정입니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionMessage(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
