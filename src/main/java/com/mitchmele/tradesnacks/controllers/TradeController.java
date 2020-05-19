package com.mitchmele.tradesnacks.controllers;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import com.mitchmele.tradesnacks.services.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TradeController {

    TradingService tradingService;

    public TradeController(TradingService tradingService) {
        this.tradingService = tradingService;
    }
    //returns date as intArray. Have to fix
    @RequestMapping("/trades")
    public @ResponseBody ResponseEntity<?> getTrades() {
        List<Trade> trades = tradingService.fetchAllTrades();
        return ResponseEntity.ok(trades);
    }
}
