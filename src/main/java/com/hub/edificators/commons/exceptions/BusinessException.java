package com.hub.edificators.commons.exceptions;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private final int errorCode;

    public BusinessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
