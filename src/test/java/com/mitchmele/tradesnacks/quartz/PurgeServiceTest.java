package com.mitchmele.tradesnacks.quartz;

import com.mitchmele.tradesnacks.mongo.TradeRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PurgeServiceTest {

    private final TradeRepository mockRepo = mock(TradeRepository.class);
    private final PurgeService purgeService = new PurgeService(mockRepo);

    @Test
    void callsRepository() {

        purgeService.executePricePurge(5.0);
        verify(mockRepo).deleteTradesByTradePriceLessThan(5.0);
    }
}