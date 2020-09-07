package com.mitchmele.tradesnacks.controllers;

import com.mitchmele.tradesnacks.models.Trade;
import com.mitchmele.tradesnacks.services.TradingService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeController.class)
class TradeControllerIntegrationTest {

    //REMOVE MOCKS THIS IS AN INTEGRATION TEST

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TradingService tradingService;

//    private final LocalDate timeOfTrade = LocalDate.of(2020, 5, 20);
    private final Date timeOfTrade = mock(Date.class);


    @Test
    public void getTrades_success_shouldCallServiceAndReturnTrades() throws Exception {

        Trade trade1 = new Trade(new ObjectId(),"ABC", 21.00, timeOfTrade, "NYSE");
        Trade trade2 = new Trade(new ObjectId(),"EEW", 51.05, timeOfTrade, "NYSE");
        Trade trade3 = new Trade(new ObjectId(),"DDY", 121.05, timeOfTrade, "NYSE");

        List<Trade> expectedTrades = asList(trade1, trade2, trade3);

        when(tradingService.fetchAllTrades()).thenReturn(expectedTrades);

        mockMvc.perform(
                get("/trades").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .is2xxSuccessful())
                .andExpect(content()
                        .string("[{\"symbol\":\"ABC\",\"tradePrice\":21.0,\"timeOfTrade\":\"2020-05-20\",\"exchange\":\"NASDAQ\"},{\"symbol\":\"EEW\",\"tradePrice\":51.05,\"timeOfTrade\":\"2020-05-20\",\"exchange\":\"NASDAQ\"},{\"symbol\":\"DDY\",\"tradePrice\":121.05,\"timeOfTrade\":\"2020-05-20\",\"exchange\":\"NASDAQ\"}]"));

        verify(tradingService).fetchAllTrades();
    }

    @Test
    public void getTradesBySymbol_success_shouldReturnListOfTradesForSymbol() throws Exception {
        Trade trade1 = new Trade(new ObjectId(),"BBY", 46.00, timeOfTrade, "NYSE");
        Trade trade2 = new Trade(new ObjectId(),"BBY", 46.05, timeOfTrade, "NYSE");

        List<Trade> trades = asList(trade1, trade2);

        when(tradingService.fetchTradesForSymbol(anyString())).thenReturn(trades);

        mockMvc.perform(
                get("/trades/bby").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[{\"symbol\":\"BBY\",\"tradePrice\":46.0,\"timeOfTrade\":\"2020-05-20\",\"exchange\":\"NASDAQ\"},{\"symbol\":\"BBY\",\"tradePrice\":46.05,\"timeOfTrade\":\"2020-05-20\",\"exchange\":\"NASDAQ\"}]"));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(tradingService).fetchTradesForSymbol(captor.capture());
        assertThat(captor.getValue()).isEqualTo("BBY");
    }
}