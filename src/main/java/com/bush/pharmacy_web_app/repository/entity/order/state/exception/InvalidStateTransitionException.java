package com.bush.pharmacy_web_app.repository.entity.order.state.exception;

public class InvalidStateTransitionException extends RuntimeException {
    public InvalidStateTransitionException(String message) {
        super(message);
    }
}
