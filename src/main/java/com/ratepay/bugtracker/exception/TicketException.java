package com.ratepay.bugtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TicketException extends RuntimeException{
    private String message;

    public TicketException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
