package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.helpers.GradleBuildLauncher;
import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("integrationTest")
class TradingServiceTestIT {

    @Autowired
    private TradingService tradingService;

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeAll
    static void beforeAll() throws URISyntaxException {
        GradleBuildLauncher buildLauncher = new GradleBuildLauncher();
//        buildLauncher.standUpMongo();
    }

    @AfterAll
    static void afterAll() throws URISyntaxException {
        GradleBuildLauncher buildLauncher = new GradleBuildLauncher();
//        buildLauncher.standDownMongo();
    }

//    @BeforeEach
//    void setUp() {
//        tradeRepository.deleteAll();
//    }

    @Test
    void serviceReturnsAllTrades() throws IOException {

        Trade trade1 = new Trade("ABC", 50.00, LocalDate.now(), "NASDAQ");
        Trade trade2 = new Trade("DDA", 1.15, LocalDate.now(), "NASDAQ");
        Trade trade3 = new Trade("TTQ", 2.50, LocalDate.now(), "NASDAQ");

        List<Trade> trades = asList(trade1, trade2, trade3);

        tradeRepository.saveAll(trades);


        List<Trade> actual = tradingService.fetchAllTrades();
        assertThat(actual).isEqualTo(trades);
    }

    @Test
    void serviceReturnsAllTradesForSymbol() throws IOException {
        Trade trade1 = new Trade("ABC", 50.00, LocalDate.now(), "NASDAQ");
        Trade trade2 = new Trade("ABC", 51.15, LocalDate.now(), "NASDAQ");
        Trade trade3 = new Trade("TTQ", 2.50, LocalDate.now(), "NASDAQ");

        List<Trade> trades = asList(trade1, trade2, trade3);

        tradeRepository.saveAll(trades);

        List<Trade> actual = tradingService.fetchTradesForSymbol("ABC");
        assertThat(actual).containsExactlyInAnyOrder(trade1, trade2);
    }
}