package com.getir.readingisgood.enums;

public enum OrderStatus {
    NEW("New"),
    FAIL("Fail"),
    COMPLETED("Received");

    private String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
