package com.alexshabanov.controller;

import com.alexshabanov.model.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping(value = "/rest", headers = "content-type=application/json")
public final class RestController {
    private final Logger log = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private Validator validator;

    private void checkValid(Object restObject) {
        final Set<?> validationResult = validator.validate(restObject);
        if (!validationResult.isEmpty()) {
            log.warn("Malformed REST object: {}, validation result: {}", restObject, validationResult);
            throw new IllegalArgumentException("Error: " + validationResult);
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    @ResponseBody
    public Hello postHello(@Valid @RequestBody Hello hello) {

        // uncomment to see validation failure
        //checkValid(hello);

        final Hello result = new Hello();
        result.setOrigin("Server + " + hello.getOrigin());
        result.setGreeting("Hello from Server + " + hello.getGreeting());
        result.setCreated(new Date());
        return result;
    }
}
