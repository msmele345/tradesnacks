package com.mitchmele.tradesnacks.services;

import com.mitchmele.tradesnacks.interstellar.config.InterstellarProperties;
import com.mitchmele.tradesnacks.interstellar.model.ExchangeResponse;
import com.mitchmele.tradesnacks.models.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class InterstellarClient {

    private final RestTemplate interstellarRestTemplate;
    private final InterstellarProperties interstellarProperties;

    public InterstellarClient(RestTemplate interstellarRestTemplate, InterstellarProperties interstellarProperties) {
        this.interstellarRestTemplate = interstellarRestTemplate;
        this.interstellarProperties = interstellarProperties;
    }

    public ExchangeResponse fetchTradesFromExchange() {

        ExchangeResponse exchangeResponse = ExchangeResponse.builder().build();

        try {
            Trade[] trades = interstellarRestTemplate.getForObject(interstellarProperties.getUrl(), Trade[].class);
            exchangeResponse.setTrades(Arrays.asList(trades));
            log.info("SUCCESSFULLY CALLED EXCHANGE AND RECEIVED RESPONSE: {}", trades);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
        return exchangeResponse;
    }
}
