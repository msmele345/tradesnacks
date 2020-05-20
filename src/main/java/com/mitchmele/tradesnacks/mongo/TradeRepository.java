package com.mitchmele.tradesnacks.mongo;

import com.mitchmele.tradesnacks.models.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TradeRepository extends MongoRepository<Trade, String> {
    List<Trade> findAllBySymbol(String symbol);
}
