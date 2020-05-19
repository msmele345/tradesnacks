package com.mitchmele.tradesnacks.mongo;


import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoClientConfiguration {


    @Override
    protected String getDatabaseName() {
        return "tradeRepo";
    }

}
