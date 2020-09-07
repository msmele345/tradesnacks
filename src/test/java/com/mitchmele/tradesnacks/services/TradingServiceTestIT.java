package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.helpers.GradleBuildLauncher;
import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("integrationTest")
class TradingServiceTestIT {

    @Autowired
    private TradingService tradingService;

    @Autowired
    private TradeRepository tradeRepository;

    private final Date timeOfTrade = new Date(50);


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

    @BeforeEach
    void setUp() {
        tradeRepository.deleteAll();
//        Trade trade1 = new Trade(new ObjectId(), "ABC", 52.07, timeOfTrade, "NASDAQ");
//        Trade trade10 = Trade.builder().symbol("NNM").timeStamp(timeOfTrade).tradePrice(89.00).build();
//        Trade trade2 = new Trade(new ObjectId(), "DDA", 2.55, timeOfTrade, "NASDAQ");
//        Trade trade3 = new Trade(new ObjectId(), "TTQ", 45.54, timeOfTrade, "NASDAQ");
//        Trade trade4 = new Trade(new ObjectId(), "FFG", 34.53, timeOfTrade, "NASDAQ");
//        Trade trade5 = new Trade(new ObjectId(), "FFG", 34.50, timeOfTrade, "NASDAQ");
//        Trade trade6 = new Trade(new ObjectId(), "JKL", 14.50, timeOfTrade, "NASDAQ");
//        Trade trade7 = new Trade(new ObjectId(), "ERT", 3.53, timeOfTrade, "NASDAQ");
//        Trade trade8 = new Trade(new ObjectId(), "AQZ", 7.50, timeOfTrade, "NASDAQ");
//        Trade trade9 = new Trade(new ObjectId(), "AQZ", 7.51, timeOfTrade, "NASDAQ");

//        List<Trade> trades = asList(trade1, trade2, trade3, trade4, trade5, trade6, trade7, trade8, trade9, trade10);

//        tradeRepository.saveAll(trades);
    }

    @Test
    void serviceReturnsAllTrades() throws IOException {

        Trade trade1 = new Trade(new ObjectId(), "ABC", 50.00, timeOfTrade, "NASDAQ");
        Trade trade2 = new Trade(new ObjectId(), "DDA", 1.15, timeOfTrade, "NASDAQ");
        Trade trade3 = new Trade(new ObjectId(), "TTQ", 2.50, timeOfTrade, "NASDAQ");

        List<Trade> trades = asList(trade1, trade2, trade3);

        tradeRepository.saveAll(trades);

        List<Trade> actual = tradingService.fetchAllTrades();
        assertThat(actual).isEqualTo(trades);
    }

    @Test
    void serviceReturnsAllTradesForSymbol() throws IOException {
        Trade trade1 = new Trade(new ObjectId(), "ZZZ", 550.00, timeOfTrade, "NYSE");
        Trade trade2 = new Trade(new ObjectId(), "ZZZ", 551.15, timeOfTrade, "NYSE");
        Trade trade3 = new Trade(new ObjectId(), "ZZZ", 552.50, timeOfTrade, "NYSE");

        List<Trade> trades = asList(trade1, trade2, trade3);

        tradingService.insertTrades(trades);

        List<Trade> expectedTrades = tradeRepository.findAllBySymbol("ZZZ");

        trades.forEach(trade -> {
            assertThat(expectedTrades).contains(trade);
        });
    }
}