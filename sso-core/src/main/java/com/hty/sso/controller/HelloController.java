package com.hty.sso.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public void hello() {
        String a=null;
        a.concat("a");
        logger.error("abcsdafdsf");
    }
}
