package com.example.board.exception;

import com.example.board.support.ApiResponse;
import com.example.board.support.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlers {
    private static final String FAIL_CODE = "fail";

    @ExceptionHandler(AuthorizationException.class)
    public ApiResponse<ApiResponse.FailureBody> handleAuthException(Exception exception, HttpServletRequest request) {
        return ApiResponseGenerator.fail(FAIL_CODE, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidException.class)
    public ApiResponse<ApiResponse.FailureBody> handleInvalidException(Exception exception, HttpServletRequest request) {
        return ApiResponseGenerator.fail(FAIL_CODE, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<ApiResponse.FailureBody> handleException(Exception exception, HttpServerErrorException requst) {
        return ApiResponseGenerator.fail(FAIL_CODE, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
