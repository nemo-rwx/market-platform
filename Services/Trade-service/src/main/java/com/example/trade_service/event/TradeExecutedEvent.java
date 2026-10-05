package com.example.trade_service.event;

import com.example.trade_service.domain.TradeSide;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TradeExecutedEvent(
            Long tradeId,
            Long portfolioId,
            Long instrumentId,
            TradeSide side,
            BigDecimal quantity,
            BigDecimal price,
            BigDecimal fees,
            String currency,
            LocalDate tradeDate
    ) {
}
