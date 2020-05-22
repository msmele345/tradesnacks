package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TradingService {

    TradeRepository tradeRepository;

    public TradingService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> fetchAllTrades() throws IOException {
        try {
            return tradeRepository.findAll();
        } catch (Exception e) {
            throw new IOException(e.getLocalizedMessage());
        }
    }

    public List<Trade> fetchTradesForSymbol(String symbol) throws IOException {
        try {
            return tradeRepository.findAllBySymbol(symbol);
        } catch (Exception e) {
            throw new IOException(e.getLocalizedMessage());
        }
    }
}
