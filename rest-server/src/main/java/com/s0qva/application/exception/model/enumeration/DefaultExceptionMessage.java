package com.s0qva.application.exception.model.enumeration;

public enum DefaultExceptionMessage {
    NO_SUCH_USER_WITH_ID("There is no user with id "),
    NO_SUCH_PRODUCT_WITH_ID("There is no user with id"),
    NO_SUCH_ORDER_WITH_ID("There is no order with id");

    private final String message;

    DefaultExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
