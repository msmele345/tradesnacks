package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.interstellar.model.ExchangeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadingService {

    private final InterstellarClient interstellarClient;
    private final TradingService tradingService;

    public void loadTrades() {

        ExchangeResponse clientTrades = interstellarClient.fetchTradesFromExchange();

        if (!clientTrades.getTrades().isEmpty()) {
            tradingService.insertTrades(clientTrades.getTrades());
        }
    }
}
