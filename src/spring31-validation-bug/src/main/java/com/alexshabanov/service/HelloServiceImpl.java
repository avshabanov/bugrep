package com.alexshabanov.service;

import org.springframework.stereotype.Service;

import com.alexshabanov.model.Hello;

import java.util.Date;

/**
 * An implementation of the hello business service.
 */
@Service
public class HelloServiceImpl implements HelloService {

    /**
     * {@inheritDoc}
     */
    public Hello getGreeting(String origin) {
        final Hello hello = new Hello();
        hello.setGreeting("Hello, there!");
        hello.setOrigin(origin);
        hello.setCreated(new Date());
        return hello;
    }
}