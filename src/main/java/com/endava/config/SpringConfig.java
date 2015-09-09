package com.endava.config;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("com.endava")
public class SpringConfig {

    @Bean
    public DB db() {
        return new MongoClient("127.0.0.1", 27017).getDB("mydb");
    }

    @Bean
    public Jongo jongo() {
        return new Jongo(db());
    }

    @Bean
    public MongoCollection mongoCollection() {
        return jongo().getCollection("test2");
    }
}
