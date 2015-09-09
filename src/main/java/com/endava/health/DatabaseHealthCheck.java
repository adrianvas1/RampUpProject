package com.endava.health;

import com.mongodb.DB;
import com.yammer.metrics.core.HealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthCheck extends HealthCheck {
    private final DB database;

    @Autowired
    public DatabaseHealthCheck(DB database) {
        super("mydb");
        this.database = database;
    }

    @Override
    protected Result check() throws Exception {
        database.getName();
        return Result.healthy();
    }
}