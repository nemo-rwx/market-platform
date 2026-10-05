package com.example.trade_service.Controller;

import com.example.trade_service.dto.CreateTradeRequest;
import com.example.trade_service.dto.TradeResponse;
import com.example.trade_service.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trades")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TradeResponse createTrader(
        @Valid@RequestBody CreateTradeRequest request){
        return tradeService.createTrade(request);
    }


}
