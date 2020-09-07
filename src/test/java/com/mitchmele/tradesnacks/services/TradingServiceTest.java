package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class TradingServiceTest {

    private TradeRepository mockRepo;

    private TradingService subject;

    private ObjectId objectId = new ObjectId();

    private final Date timeOfTrade = mock(Date.class);

    @BeforeEach
    void setUp() {
        mockRepo = mock(TradeRepository.class);
        subject = new TradingService(mockRepo);
    }

    @Test
    public void fetchAllTrades_success_shouldCallTheRep() throws IOException {
        Trade trade1 = new Trade(objectId, "ABC", 50.00, timeOfTrade, "NASDAQ");
        Trade trade2 = new Trade(objectId, "ABC", 51.00, timeOfTrade, "NASDAQ");
        Trade trade3 = new Trade(objectId, "ABC", 52.00, timeOfTrade, "NASDAQ");

        List<Trade> expected = asList(trade1, trade2, trade3);

        when(mockRepo.findAll()).thenReturn(expected);

        subject.fetchAllTrades();
        verify(mockRepo).findAll();
    }

    @Test
    public void fetchAllTrades_success_shouldReturnListOfTrades() throws IOException {
        Trade trade1 = new Trade(objectId, "ABC", 50.00, timeOfTrade, "NASDAQ");
        Trade trade2 = new Trade(objectId, "ABC", 51.00, timeOfTrade, "NASDAQ");
        Trade trade3 = new Trade(objectId, "ABC", 52.00, timeOfTrade, "NASDAQ");

        List<Trade> expected = asList(trade1, trade2, trade3);

        when(mockRepo.findAll()).thenReturn(expected);

        List<Trade> actual = subject.fetchAllTrades();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fetchTradesBySymbol_success_shouldCallRepoFindAllBySymbol() throws IOException {
        Trade trade1 = new Trade(objectId, "SPY", 125.00, timeOfTrade, "NYSE");
        Trade trade2 = new Trade(objectId, "SPY", 126.00, timeOfTrade, "NYSE");

        List<Trade> trades = asList(trade1, trade2);

        when(mockRepo.findAllBySymbol(any())).thenReturn(trades);

        subject.fetchTradesForSymbol("SPY");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockRepo).findAllBySymbol(captor.capture());
    }

    @Test
    public void fetchTradesBySymbol_success_shouldReturnTradesBySymbol() throws IOException {
        Trade trade1 = new Trade(objectId, "SPY", 125.00, timeOfTrade, "NYSE");
        Trade trade2 = new Trade(objectId, "SPY", 126.00, timeOfTrade, "NYSE");

        List<Trade> trades = asList(trade1, trade2);

        when(mockRepo.findAllBySymbol(any())).thenReturn(trades);

        List<Trade> actual = subject.fetchTradesForSymbol("SPY");
        assertThat(actual).isEqualTo(trades);
    }

    @Test
    public void fetchAllTrades_failure_shouldThrowIOExceptionIfRepoFails() {

        when(mockRepo.findAll()).thenThrow(new RuntimeException("bad news bears"));

        assertThatThrownBy(() -> subject.fetchAllTrades())
                .isInstanceOf(IOException.class)
                .hasMessage("bad news bears");
    }

    @Test
    public void fetchAllTradesForSymbol_failure_shouldThrowIOExceptionIfRepoFails() {

        when(mockRepo.findAllBySymbol(anyString())).thenThrow(new RuntimeException("bad news for this symbol"));

        assertThatThrownBy(() -> subject.fetchTradesForSymbol("BAD"))
                .isInstanceOf(IOException.class)
                .hasMessage("bad news for this symbol");
    }

    @Test
    void insertTrades_callsRepositorySaveAllWithListOfTrades() {
        Trade trade1 = new Trade(objectId, "SPY", 125.00, timeOfTrade, "NYSE");
        Trade trade2 = new Trade(objectId, "SPY", 126.00, timeOfTrade, "NYSE");

        List<Trade> trades = asList(trade1, trade2);

        subject.insertTrades(trades);
        verify(mockRepo).saveAll(trades);
    }
}