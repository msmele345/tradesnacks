package com.mitchmele.tradesnacks.services;


import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TradingServiceTest {


    TradeRepository mockRepo;

    TradingService subject;


    @BeforeEach
    void setUp() {
        mockRepo = mock(TradeRepository.class);
        subject = new TradingService(mockRepo);
    }


    @Test
    public void fetchAllTrades_success_shouldCallTheRep() {
        Trade trade1 = new Trade("ABC", 50.00, LocalDate.now(), "NASDAQ");
        Trade trade2 = new Trade("ABC", 51.00, LocalDate.now(), "NASDAQ");
        Trade trade3 = new Trade("ABC", 52.00, LocalDate.now(), "NASDAQ");

        List<Trade> expected = asList(trade1, trade2, trade3);

        when(mockRepo.findAll()).thenReturn(expected);

        subject.fetchAllTrades();
        verify(mockRepo).findAll();
    }

    @Test
    public void fetchAllTrades_success_shouldReturnListOfTrades() {
        Trade trade1 = new Trade("ABC", 50.00, LocalDate.now(), "NASDAQ");
        Trade trade2 = new Trade("ABC", 51.00, LocalDate.now(), "NASDAQ");
        Trade trade3 = new Trade("ABC", 52.00, LocalDate.now(), "NASDAQ");

        List<Trade> expected = asList(trade1, trade2, trade3);

        when(mockRepo.findAll()).thenReturn(expected);

        List<Trade> actual = subject.fetchAllTrades();

        assertThat(actual).isEqualTo(expected);
    }
}