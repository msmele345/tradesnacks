package com.mitchmele.tradesnacks.controllers;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.services.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradingService tradingService;

    //returns date as intArray. Have to fix
    //change exception
    @GetMapping("/")
    public ResponseEntity<?> getTrades() throws IOException {
        List<Trade> trades = tradingService.fetchAllTrades();
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getTradesBySymbol(@PathVariable String symbol) throws IOException {
        List<Trade> trades = tradingService.fetchTradesForSymbol(symbol.toUpperCase());
        return ResponseEntity.ok().body(trades);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createTrade( @RequestBody String trade) {
        Trade newTrade = tradingService.insertTrade(trade); //fix later
        return ResponseEntity.ok(newTrade);
    }
}
