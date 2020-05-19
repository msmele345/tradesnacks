package com.mitchmele.tradesnacks.mongo;

import com.mitchmele.tradesnacks.models.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TradeRepository extends MongoRepository<Trade, String> {
}
