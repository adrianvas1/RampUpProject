package com.endava.config;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class MessagesConfiguration {

    @NotNull
    private String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}