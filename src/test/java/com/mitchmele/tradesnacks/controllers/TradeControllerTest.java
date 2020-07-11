package com.mitchmele.tradesnacks.controllers;


import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.services.TradingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TradeControllerTest {


    TradeController subject;

    TradingService mockService;

    MockMvc mockMvc;

    private final LocalDate timeOfTrade = LocalDate.of(2020, 5, 19);

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


        Trade trade1 = new Trade("ABC", 50.00, timeOfTrade, "NASDAQ");
        Trade trade2 = new Trade("ABC", 51.00, timeOfTrade, "NASDAQ");
        Trade trade3 = new Trade("ABC", 52.00, timeOfTrade, "NASDAQ");

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


    @Test
    public void getTradesBySymbol_success_shouldCallService() throws Exception {

        mockMvc
                .perform(
                        get("/trades/spy")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(mockService).fetchTradesForSymbol("SPY");
    }


    @Test
    public void getTradesBySymbol_success_shouldReturnTradesForSymbol() throws Exception {

        Trade trade1 = new Trade("SPY", 150.00, timeOfTrade, "NASDAQ");
        Trade trade2 = new Trade("SPY", 151.00, timeOfTrade, "NASDAQ");

        List<Trade> trades = asList(trade1, trade2);

        when(mockService.fetchTradesForSymbol(any())).thenReturn(trades);

        String expectedResponse = "[{\"symbol\":\"SPY\",\"tradePrice\":150.0,\"timeOfTrade\":[2020,5,19],\"exchange\":\"NASDAQ\"},{\"symbol\":\"SPY\",\"tradePrice\":151.0,\"timeOfTrade\":[2020,5,19],\"exchange\":\"NASDAQ\"}]";
        mockMvc
                .perform(
                        get("/trades/spy")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void createTrade_success_shouldCallPostTradeOnService() throws Exception {

        String incomingTrade = "{\"symbol\":\"SPY\",\"tradePrice\":150.0,\"timeOfTrade\":{\"year\":2020,\"month\":\"MAY\",\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":140,\"era\":\"CE\",\"leapYear\":true,\"monthValue\":5,\"dayOfMonth\":19,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"exchange\":\"NASDAQ\"}";

        String expectedResponse = "{\"symbol\":\"SPY\",\"tradePrice\":150.0,\"timeOfTrade\":[2020,5,19],\"exchange\":\"NASDAQ\"}";

        Trade expectedContent = new Trade("SPY", 150.00, timeOfTrade, "NASDAQ");

        when(mockService.insertTrade(any())).thenReturn(expectedContent);

        MvcResult mvcResult = mockMvc
                .perform(post("/trades/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incomingTrade)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        verify(mockService).insertTrade(any());

        MockHttpServletResponse actualResponse = mvcResult.getResponse();
        assertThat(actualResponse.getContentAsString()).isEqualTo(expectedResponse);
    }
}