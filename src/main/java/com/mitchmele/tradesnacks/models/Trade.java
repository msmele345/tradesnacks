package com.mitchmele.tradesnacks.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Trade {

    String symbol;
    Double tradePrice;
    LocalDate timeOfTrade;
    String exchange;


    public Trade(String symbol, Double tradePrice, LocalDate timeOfTrade, String exchange) {
        this.symbol = symbol;
        this.tradePrice = tradePrice;
        this.timeOfTrade = timeOfTrade;
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public LocalDate getTimeOfTrade() {
        return timeOfTrade;
    }

    public String getExchange() {
        return exchange;
    }
}
