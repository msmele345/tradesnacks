package com.mitchmele.tradesnacks.quartz;

import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.springframework.stereotype.Service;

@Service
public class PurgeService {

    private TradeRepository tradeRepository;

    public PurgeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public void executePricePurge(Double price) {
        tradeRepository.deleteTradesByTradePriceLessThan(price);
    }
}
