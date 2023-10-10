package com.example.board.exception;

public class InputValidationException extends IllegalArgumentException {

    public InputValidationException(String message) {
        super(message);
    }
}
