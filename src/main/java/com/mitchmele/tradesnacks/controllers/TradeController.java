package com.mitchmele.tradesnacks.controllers;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.services.TradingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TradeController {

    TradingService tradingService;

    public TradeController(TradingService tradingService) {
        this.tradingService = tradingService;
    }
    //returns date as intArray. Have to fix
    @GetMapping("/trades")
    public @ResponseBody ResponseEntity<?> getTrades() {
        List<Trade> trades = tradingService.fetchAllTrades();
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/trades/{symbol}")
    public ResponseEntity<?> getTradesBySymbol(@PathVariable String symbol) {
        List<Trade> trades = tradingService.fetchTradesForSymbol(symbol);
        return ResponseEntity.ok(trades);
    }
}
