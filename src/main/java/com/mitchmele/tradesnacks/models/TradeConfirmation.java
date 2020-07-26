package com.mitchmele.tradesnacks.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeConfirmation {

    private String status;
    private String symbol;
    private Double fillPrice;
}
