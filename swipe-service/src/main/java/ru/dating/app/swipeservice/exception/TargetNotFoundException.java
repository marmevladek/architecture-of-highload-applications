package ru.dating.app.swipeservice.exception;

import lombok.Data;

@Data
public class TargetNotFoundException extends RuntimeException {
    private String errorCode;
    private int status;

    public TargetNotFoundException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
