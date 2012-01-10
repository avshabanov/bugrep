package com.alexshabanov.service;

import com.alexshabanov.model.Hello;

/**
 * Hello business service.
 */
public interface HelloService {
    Hello getGreeting(String origin);
}