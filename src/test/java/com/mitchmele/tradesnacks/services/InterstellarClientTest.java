package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.interstellar.config.InterstellarProperties;
import com.mitchmele.tradesnacks.interstellar.model.ExchangeResponse;
import com.mitchmele.tradesnacks.models.Trade;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class InterstellarClientTest {

    private RestTemplate restTemplate;
    private InterstellarProperties interstellarProperties;
    private InterstellarClient subject;

    private final Date timeOfTrade = mock(Date.class);

    @BeforeEach
    void setUp() {

        restTemplate = mock(RestTemplate.class);
        interstellarProperties = mock(InterstellarProperties.class);
        subject = new InterstellarClient(restTemplate, interstellarProperties);
    }

    @Test
    void getTrades_callsEndpointWithCorrectUrl() {

        ObjectId objectId = new ObjectId();

        Trade trade1 = new Trade(objectId, "ABC", 50.00, timeOfTrade, "NASDAQ");
        Trade trade2 = new Trade(objectId, "ABC", 51.00, timeOfTrade, "NASDAQ");
        Trade trade3 = new Trade(objectId, "ABC", 52.00, timeOfTrade, "NASDAQ");

        when(interstellarProperties.getUrl()).thenReturn("url");

        ExchangeResponse expectedResponse = ExchangeResponse
                .builder()
                .trades(asList(trade1, trade2, trade3))
                .build();

        Trade[] trades = {trade1, trade2, trade3};
        when(restTemplate.getForObject(anyString(), any(), (Object) any())).thenReturn(trades);

        ExchangeResponse actual = subject.fetchTradesFromExchange();

        verify(interstellarProperties).getUrl();
        verify(restTemplate).getForObject("url", Trade[].class);
        assertThat(actual).isEqualTo(expectedResponse);
    }
}