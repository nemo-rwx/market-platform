package com.example.trade_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTradeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTrade(
            InvalidTradeException ex) {

        return new ErrorResponse(
                "INVALID_TRADE",
                ex.getMessage()
        );
    }

    public record ErrorResponse(
            String code,
            String message
    ) {
    }
}