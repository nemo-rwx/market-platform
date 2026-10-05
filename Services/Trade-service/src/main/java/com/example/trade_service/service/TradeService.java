package com.example.trade_service.service;


import com.example.trade_service.domain.Trade;
import com.example.trade_service.domain.TradeStatus;
import com.example.trade_service.dto.CreateTradeRequest;
import com.example.trade_service.dto.TradeResponse;
import com.example.trade_service.repository.TradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class TradeService {

    public final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Transactional
    public TradeResponse createTrade(CreateTradeRequest request) {

        Trade trade = new Trade();

        trade.setExternalTradeId(UUID.randomUUID().toString());
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

        String externalTradeID = String.format("TRD-%s-%06d",
                savedTrade.getTradeDate().format(DateTimeFormatter.BASIC_ISO_DATE),
                 savedTrade.getId()
        );

        savedTrade.setExternalTradeId(externalTradeID);
        savedTrade = tradeRepository.save(savedTrade);



        return new TradeResponse(
                savedTrade.getId(),
                savedTrade.getExternalTradeId(),
                savedTrade.getPortfolioId(),
                savedTrade.getInstrumentId(),
                savedTrade.getSide(),
                savedTrade.getQuantity(),
                savedTrade.getPrice(),
                savedTrade.getFees(),
                savedTrade.getCurrency(),
                savedTrade.getTradeDate(),
                savedTrade.getStatus()
        );
    }

}
