package com.mitchmele.tradesnacks.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "trades")
public class Trade {

    String symbol;
    Double tradePrice;
    LocalDate timeOfTrade;
    String exchange;

    final static String DEFAULT_EXCHANGE = "NASDAQ";

    public Trade(String symbol, Double tradePrice, LocalDate timeOfTrade, String exchange) {
        this.symbol = symbol;
        this.tradePrice = tradePrice;
        this.timeOfTrade = timeOfTrade;
        this.exchange = DEFAULT_EXCHANGE;
    }
}
