package com.mitchmele.tradesnacks.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.services.TradingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TradeControllerTest {


    TradeController subject;

    TradingService mockService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockService = mock(TradingService.class);
        subject = new TradeController(mockService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(subject)
                .build();
    }

    @Test
    public void getTrades_success_shouldReturn200AndCallService() throws Exception {

        mockMvc.perform(
                get("/trades/")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(mockService).fetchAllTrades();
    }

    @Test
    public void getTrades_success_shouldReturn200AndTradesFromService() throws Exception {

        Trade trade1 = new Trade("ABC", 50.00, LocalDate.now(), "NASDAQ");
        Trade trade2 = new Trade("ABC", 51.00, LocalDate.now(), "NASDAQ");
        Trade trade3 = new Trade("ABC", 52.00, LocalDate.now(), "NASDAQ");

        List<Trade> trades = asList(trade1, trade2, trade3);

        when(mockService.fetchAllTrades()).thenReturn(trades);

        String expected = "[{\"symbol\":\"ABC\",\"tradePrice\":50.0,\"timeOfTrade\":[2020,5,19],\"exchange\":\"NASDAQ\"},{\"symbol\":\"ABC\",\"tradePrice\":51.0,\"timeOfTrade\":[2020,5,19],\"exchange\":\"NASDAQ\"},{\"symbol\":\"ABC\",\"tradePrice\":52.0,\"timeOfTrade\":[2020,5,19],\"exchange\":\"NASDAQ\"}]";

        mockMvc.perform(
                get("/trades/")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(expected));
    }
}