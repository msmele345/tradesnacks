package com.mitchmele.tradesnacks.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.models.TradeConfirmation;
import com.mitchmele.tradesnacks.services.LoadingService;
import com.mitchmele.tradesnacks.services.TradingService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TradeControllerTest {

    private TradeController subject;

    private TradingService mockService;

    private LoadingService mockLoadingService;

    private MockMvc mockMvc;

    private ObjectId objectId;

    private final Date timeStamp = mock(Date.class);

    @BeforeEach
    void setUp() {

        objectId = new ObjectId(new Date());
        mockLoadingService = mock(LoadingService.class);
        mockService = mock(TradingService.class);
        subject = new TradeController(mockService, mockLoadingService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(subject)
                .build();
    }

    @Test
    public void getTrades_success_shouldReturn200AndCallService() throws Exception {

        mockMvc.perform(
                get("/api/v1/trades/")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(mockService).fetchAllTrades();
    }

    @Test
    public void getTrades_success_shouldReturn200AndTradesFromService() throws Exception {


        Trade trade1 = new Trade(objectId, "ABC", 50.00, timeStamp, "NASDAQ");
        Trade trade2 = new Trade(objectId, "ABC", 51.00, timeStamp, "NASDAQ");
        Trade trade3 = new Trade(objectId, "ABC", 52.00, timeStamp, "NASDAQ");

        List<Trade> trades = asList(trade1, trade2, trade3);

        when(mockService.fetchAllTrades()).thenReturn(trades);

        mockMvc.perform(
                get("/api/v1/trades/")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(mockService).fetchAllTrades();
    }


    @Test
    public void getTradesBySymbol_success_shouldCallService() throws Exception {

        mockMvc
                .perform(
                        get("/api/v1/trades/spy")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(mockService).fetchTradesForSymbol("SPY");
    }


    @Test
    public void getTradesBySymbol_success_shouldReturnTradesForSymbol() throws Exception {

        Trade trade1 = new Trade(objectId,"SPY", 150.00, timeStamp, "NASDAQ");
        Trade trade2 = new Trade(objectId,"SPY", 151.00, timeStamp, "NASDAQ");

        List<Trade> trades = asList(trade1, trade2);

        when(mockService.fetchTradesForSymbol(any())).thenReturn(trades);

        mockMvc
                .perform(
                        get("/api/v1/trades/spy")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(mockService).fetchTradesForSymbol("SPY");
    }

    @Test
    public void createTrade_success_shouldCallPostTradeOnService() throws Exception {

        String incomingTrade = "{\"symbol\":\"SPY\",\"tradePrice\":150.0,\"timeOfTrade\":{\"year\":2020,\"month\":\"MAY\",\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":140,\"era\":\"CE\",\"leapYear\":true,\"monthValue\":5,\"dayOfMonth\":19,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"exchange\":\"NASDAQ\"}";

        Trade expectedContent = new Trade(objectId,"SPY", 150.00, timeStamp, "NASDAQ");
        when(mockService.insertTrade(any())).thenReturn(expectedContent);

        TradeConfirmation expectedConfirm = TradeConfirmation.builder()
                .symbol("SPY")
                .fillPrice(150.00)
                .status("Success")
                .build();

        ObjectMapper mapper = new ObjectMapper();

        String expectedResponse = mapper.writeValueAsString(expectedConfirm);

         mockMvc
                .perform(post("/api/v1/trades/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incomingTrade)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(expectedResponse));

        verify(mockService).insertTrade(any());
    }

    @Test
    void loadTradesFromExchange_callsServiceToInsertAll() throws Exception {

        mockMvc
                .perform(post("/api/v1/trades"))
                .andExpect(status().isOk());

        verify(mockLoadingService).loadTrades();
    }
}