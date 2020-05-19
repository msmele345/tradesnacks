package com.mitchmele.tradesnacks.controllers;

import com.mitchmele.tradesnacks.mongo.TradeRepository;
import com.mitchmele.tradesnacks.services.TradingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TradeRepository tradeRepository;

    @MockBean
    TradingService tradingService;


    @Test
    public void getTrades_success_shouldReturn200() throws Exception {

        mockMvc.perform(
                get("/trades").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("some body"));

        verify(tradingService).fetchAllTrades();
    }
}