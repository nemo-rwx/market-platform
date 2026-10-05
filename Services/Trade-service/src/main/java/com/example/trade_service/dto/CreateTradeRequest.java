package com.example.trade_service.dto;

import com.example.trade_service.domain.TradeSide;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTradeRequest (@NotNull
                                  Long portfolioId,

                                  @NotNull
                                  Long instrumentId,

                                  @NotNull
                                  TradeSide side,

                                  @Positive
                                  BigDecimal quantity,

                                  @Positive
                                  BigDecimal price,
                                  @Positive
                                  BigDecimal fees,
                                  @NotBlank
                                  String currency,
                                  @NotNull
                                  LocalDate tradeDate
) {


}
