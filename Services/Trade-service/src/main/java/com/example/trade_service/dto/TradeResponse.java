package com.example.trade_service.dto;
import com.example.trade_service.domain.TradeSide;
import com.example.trade_service.domain.TradeStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TradeResponse(
        Long id,
        String externalTradeId,
        Long portfolioId,
        Long instrumentId,
        TradeSide side,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal fees,
        String currency,
        LocalDate tradeDate,
        TradeStatus status
) {
}

