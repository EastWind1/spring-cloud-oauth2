package com.test.cloud.servicea.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RefreshScope
public class RootController {
    @Value("${test-value}")
    private String config;
    @RequestMapping("")
    public String index() {
        return "this is service a";
    }
    @RequestMapping("config")
    public String getConfig() {
        return String.format("get config from git [%s]", config);
    }
}
