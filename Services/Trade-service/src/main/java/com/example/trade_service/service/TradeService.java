package com.example.trade_service.service;

import com.example.trade_service.Exceptions.InvalidTradeException;
import com.example.trade_service.Exceptions.TradeNotFoundException;
import com.example.trade_service.domain.Trade;
import com.example.trade_service.domain.TradeStatus;
import com.example.trade_service.dto.CreateTradeRequest;
import com.example.trade_service.dto.TradeResponse;
import com.example.trade_service.event.TradeEventPublisher;
import com.example.trade_service.event.TradeExecutedEvent;
import com.example.trade_service.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeEventPublisher tradeEventPublisher;

    public TradeService(
            TradeRepository tradeRepository,
            TradeEventPublisher tradeEventPublisher) {

        this.tradeRepository = tradeRepository;
        this.tradeEventPublisher = tradeEventPublisher;
    }

    @Transactional
    public TradeResponse createTrade(CreateTradeRequest request) {

        Trade trade = new Trade();

        trade.setPortfolioId(request.portfolioId());
        trade.setInstrumentId(request.instrumentId());
        trade.setSide(request.side());
        trade.setQuantity(request.quantity());
        trade.setPrice(request.price());
        trade.setFees(request.fees());
        trade.setCurrency(request.currency());
        trade.setTradeDate(request.tradeDate());
        trade.setStatus(TradeStatus.NEW);

        Trade savedTrade = tradeRepository.save(trade);

        String externalTradeId = String.format(
                "TRD-%s-%06d",
                savedTrade.getTradeDate()
                        .format(DateTimeFormatter.BASIC_ISO_DATE),
                savedTrade.getId()
        );

        savedTrade.setExternalTradeId(externalTradeId);

        savedTrade = tradeRepository.save(savedTrade);

        return toResponse(savedTrade);
    }

    @Transactional(readOnly = true)
    public TradeResponse getTrade(Long id) {

        Trade trade = getTradeOrThrow(id);

        return toResponse(trade);
    }

    @Transactional
    public TradeResponse executeTrade(Long id) {

        Trade trade = getTradeOrThrow(id);

        if (trade.getStatus() != TradeStatus.NEW) {

            throw new InvalidTradeException(
                    "Trade cannot be executed because its current status is "
                            + trade.getStatus()
            );
        }

        trade.setStatus(TradeStatus.EXECUTED);

        Trade savedTrade = tradeRepository.save(trade);

        TradeExecutedEvent event = new TradeExecutedEvent(
                savedTrade.getId(),
                savedTrade.getPortfolioId(),
                savedTrade.getInstrumentId(),
                savedTrade.getSide(),
                savedTrade.getQuantity(),
                savedTrade.getPrice(),
                savedTrade.getFees(),
                savedTrade.getCurrency(),
                savedTrade.getTradeDate()
        );

        tradeEventPublisher.publishTradeExecuted(event);

        return toResponse(savedTrade);
    }

    @Transactional
    public TradeResponse cancelTrade(Long id) {

        Trade trade = getTradeOrThrow(id);

        if (trade.getStatus() != TradeStatus.NEW) {

            throw new InvalidTradeException(
                    "Trade cannot be cancelled because its current status is "
                            + trade.getStatus()
            );
        }

        trade.setStatus(TradeStatus.CANCELLED);

        Trade savedTrade = tradeRepository.save(trade);

        return toResponse(savedTrade);
    }

    private Trade getTradeOrThrow(Long id) {

        return tradeRepository.findById(id)
                .orElseThrow(() ->
                        new TradeNotFoundException(
                                "Trade not found: " + id
                        )
                );
    }

    private TradeResponse toResponse(Trade trade) {

        return new TradeResponse(
                trade.getId(),
                trade.getExternalTradeId(),
                trade.getPortfolioId(),
                trade.getInstrumentId(),
                trade.getSide(),
                trade.getQuantity(),
                trade.getPrice(),
                trade.getFees(),
                trade.getCurrency(),
                trade.getTradeDate(),
                trade.getStatus()
        );
    }
}