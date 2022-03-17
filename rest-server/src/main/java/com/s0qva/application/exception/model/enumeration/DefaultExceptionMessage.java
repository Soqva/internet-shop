package com.s0qva.application.exception.model.enumeration;

public enum DefaultExceptionMessage {
    NO_SUCH_USER_WITH_ID("There is no user with id: "),
    NO_SUCH_USER_WITH_USERNAME("There is no user with username: "),
    NO_SUCH_PRODUCT_WITH_ID("There is no user with id: "),
    NO_SUCH_ORDER_WITH_ID("There is no order with id: "),
    USER_ALREADY_EXISTS_WITH_USERNAME("User already exists with username: "),
    INVALID_PASSWORD_DURING_SIGN_IN("Passwords don't match");

    private final String message;

    DefaultExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
