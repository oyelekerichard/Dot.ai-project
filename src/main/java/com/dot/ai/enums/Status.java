package com.dot.ai.enums;

public enum Status {
    SUCCESS(200, "Success"),
    FAILED(400, "Failed"),
    BAD_REQUEST(400, "Bad Request"),
    INVALID_EMAIL(400, "Invalid Email"),
    NULL_REQUEST(400, "Request Body is null"),
    INVALID_USERNAME(400, "Username cannot be empty"),
    INVALID_PARAMETER(400, "Invalid Parameter"),
    RECORD_EXISTS(400, "Record already exists"),
    USER_EXISTS(400, "User Already Exists");

    private final int code;
    private final String message;

    private Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getStatusCode() {
        return this.code;
    }

    public String getStatusMessage() {
        return this.message;
    }
}
