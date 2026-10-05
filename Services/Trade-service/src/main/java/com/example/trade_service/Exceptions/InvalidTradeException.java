package com.example.trade_service.Exceptions;

public class InvalidTradeException extends RuntimeException {

    public InvalidTradeException(String message) {

        super(message);
    }
}