package com.mitchmele.tradesnacks.interstellar.model;

import com.mitchmele.tradesnacks.models.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponse {

    private List<Trade> trades;

    public List<Trade> getTrades() {
        return nonNull(trades) ? trades : emptyList();
    }
}
