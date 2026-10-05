package com.example.trade_service.Controller;

import com.example.trade_service.dto.CreateTradeRequest;
import com.example.trade_service.dto.TradeResponse;
import com.example.trade_service.event.TradeEventPublisher;
import com.example.trade_service.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trades")
public class TradeController {

    private final TradeService tradeService;
    private final TradeEventPublisher tradeEventPublisher;

    public TradeController(TradeService tradeService, TradeEventPublisher tradeEventPublisher) {
        this.tradeService = tradeService;
        this.tradeEventPublisher = tradeEventPublisher;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TradeResponse createTrader(
        @Valid@RequestBody CreateTradeRequest request){
        return tradeService.createTrade(request);
    }

    @GetMapping("/{id}")
    public TradeResponse getTrade(@PathVariable Long id) {
        return tradeService.getTrade(id);
    }

    @PostMapping("/{id}/execute")
    public TradeResponse executeTrade(@PathVariable Long id) {
        return tradeService.executeTrade(id);
    }

    @PostMapping("/{id}/cancel")
    public TradeResponse cancelTrade(@PathVariable Long id) {
        return tradeService.cancelTrade(id);
    }


}
