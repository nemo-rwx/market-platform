package com.example.trade_service.Exceptions;

public class TradeNotFoundException extends RuntimeException {

    public TradeNotFoundException(String message) {
        super(message);
    }
}