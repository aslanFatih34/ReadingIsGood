package com.getir.readingisgood.enums;

import org.springframework.http.HttpStatus;

public enum ErrorMessages {
    CUSTOMER_COULD_NOT_FOUND("Customer could not be found.", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("Email already exists.", HttpStatus.BAD_REQUEST);

    private String message;
    private HttpStatus httpStatus;

    ErrorMessages(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
