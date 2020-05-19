package com.mitchmele.tradesnacks.services;


import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TradingService {

    TradeRepository tradeRepository;

    public TradingService(TradeRepository tradeRepository) {
            this.tradeRepository = tradeRepository;
    }

    public List<Trade> fetchAllTrades() {
        return tradeRepository.findAll();
    }
}
