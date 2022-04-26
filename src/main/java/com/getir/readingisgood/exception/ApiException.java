package com.getir.readingisgood.exception;

import com.getir.readingisgood.enums.ErrorMessages;

public class ApiException extends RuntimeException {
    private ErrorMessages errorMessages;

    public ApiException(ErrorMessages errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }

    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }
}
