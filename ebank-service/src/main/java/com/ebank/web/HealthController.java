package com.ebank.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private static Logger log = LoggerFactory.getLogger(HealthController.class);

    @RequestMapping(value = "/")
    public String home() {
        log.info("Access /");
        return "Hi!";
    }
}
