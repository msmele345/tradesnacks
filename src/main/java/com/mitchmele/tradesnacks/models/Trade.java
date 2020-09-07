package com.mitchmele.tradesnacks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trades")
public class Trade {

    @Id
    private ObjectId _id;

    private String symbol;
    private Double tradePrice;
    private Date timeStamp;
    private String exchange;


    final static String DEFAULT_EXCHANGE = "NASDAQ";

    public String getExchange() {
        return DEFAULT_EXCHANGE;
    }
}
