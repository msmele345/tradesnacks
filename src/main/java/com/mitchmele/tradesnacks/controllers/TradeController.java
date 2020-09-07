package com.mitchmele.tradesnacks.controllers;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.models.TradeConfirmation;
import com.mitchmele.tradesnacks.services.LoadingService;
import com.mitchmele.tradesnacks.services.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TradeController {

    private final TradingService tradingService;
    private final LoadingService loadingService;

    //returns date as intArray. Have to fix
    //create app to have rest endpoint that fetches trades from sql and enters them in mongo
    @GetMapping("/trades")
    @CrossOrigin
    public List<Trade> getTrades() throws IOException {
        return tradingService.fetchAllTrades();
    }

    @GetMapping("/trades/{symbol}")
    @CrossOrigin
    public List<Trade> getTradesBySymbol(@PathVariable String symbol) throws IOException {
        return tradingService.fetchTradesForSymbol(symbol.toUpperCase());
    }

    @PostMapping("/trades")
    @CrossOrigin
    public void loadTradesFromExchange() {
        loadingService.loadTrades();
    }

    @PostMapping(value = "/trades/create")
    public TradeConfirmation createTrade(@RequestBody String newTrade) {
        Trade trade = tradingService.insertTrade(newTrade);
        return TradeConfirmation.builder()
                .status("Success")
                .symbol(trade.getSymbol())
                .fillPrice(trade.getTradePrice())
                .build();
    }
}
