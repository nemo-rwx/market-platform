package com.example.trade_service.domain;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalTradeId;

    private Long portfolioId;

    private Long instrumentId;

    @Enumerated(EnumType.STRING)
    private TradeSide side;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal fees;

    private String currency;

    private LocalDate tradeDate;

    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalTradeId() {
        return externalTradeId;
    }

    public void setExternalTradeId(String externalTradeId) {
        this.externalTradeId = externalTradeId;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public TradeSide getSide() {
        return side;
    }

    public void setSide(TradeSide side) {
        this.side = side;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }
}