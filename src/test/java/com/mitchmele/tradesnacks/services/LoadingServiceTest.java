package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.interstellar.model.ExchangeResponse;
import com.mitchmele.tradesnacks.models.Trade;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

class LoadingServiceTest {

    private TradingService tradingService;
    private InterstellarClient interstellarClient;
    private LoadingService subject;

    private final Date timeOfTrade = mock(Date.class);


    @BeforeEach
    void setUp() {

        tradingService = mock(TradingService.class);
        interstellarClient = mock(InterstellarClient.class);
        subject = new LoadingService(interstellarClient, tradingService);
    }

    @Test
    void loadTrades_callsClientForRestCallToServer() {

        ObjectId objectId = new ObjectId();
        Trade trade1 = new Trade(objectId, "ABC", 50.00, timeOfTrade, "NASDAQ");
        Trade trade2 = new Trade(objectId, "ABC", 51.00, timeOfTrade, "NASDAQ");
        Trade trade3 = new Trade(objectId, "ABC", 52.00, timeOfTrade, "NASDAQ");

        List<Trade> trades = asList(trade1, trade2, trade3);
        ExchangeResponse expectedResponse = ExchangeResponse
                .builder()
                .trades(trades)
                .build();

        when(interstellarClient.fetchTradesFromExchange()).thenReturn(expectedResponse);

        subject.loadTrades();
        verify(tradingService).insertTrades(trades);
    }

    @Test
    void loadTrades_doesNotCallServiceIfResponseFromExchangeIsEmpty() {

        ExchangeResponse expectedResponse = ExchangeResponse
                .builder()
                .trades(emptyList())
                .build();

        when(interstellarClient.fetchTradesFromExchange()).thenReturn(expectedResponse);

        subject.loadTrades();
        verifyZeroInteractions(tradingService);
    }
}