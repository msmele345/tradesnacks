package com.mitchmele.tradesnacks.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchmele.tradesnacks.models.ErrorResponse;
import com.mitchmele.tradesnacks.services.LoadingService;
import com.mitchmele.tradesnacks.services.TradingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestAdviceTest {

    private TradingService tradingService;

    private LoadingService loadingService;

    private TradeController tradeController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        tradingService = mock(TradingService.class);
        loadingService = mock(LoadingService.class);
        tradeController = new TradeController(tradingService, loadingService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(tradeController)
                .setControllerAdvice(new RestAdvice())
                .build();
    }

    @Test
    void internalServerError_returnsErrorResponseWithStatusCode() throws Exception {

        ErrorResponse expectedResponse = ErrorResponse.builder().exceptionMsg("bad error").exception("RuntimeException").build();

        when(tradingService.fetchAllTrades()).thenThrow(new RuntimeException("bad error"));
        ObjectMapper mapper = new ObjectMapper();

        String expectedErrorResponse = mapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/api/v1/trades"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(expectedErrorResponse));
    }
}

