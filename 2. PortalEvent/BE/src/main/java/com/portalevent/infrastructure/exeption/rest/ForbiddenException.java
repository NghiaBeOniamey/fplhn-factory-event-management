package com.portalevent.infrastructure.exeption.rest;

import com.portalevent.infrastructure.constant.Message;

/**
 * @author SonPT
 */
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public ForbiddenException() {
    }

    public ForbiddenException(Message statusCode) {
        this.message = statusCode.getMessage();
    }

    public ForbiddenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
