package com.example.board.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {
    INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, "이메일에 '@'가 없습니다."),
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "비밀번호 길이가 8글자 미만입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "등록된 가입자 정보가 없습니다."),
    PASSWORD_IS_NOT_RIGHT(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionMessage(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
