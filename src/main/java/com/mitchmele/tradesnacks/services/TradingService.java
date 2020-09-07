package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final TradeRepository tradeRepository;

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

    public void insertTrades(List<Trade> trades) {
        tradeRepository.saveAll(trades);
    }

    public Trade insertTrade(String jsonTrade) {
        return null;
    }
}
