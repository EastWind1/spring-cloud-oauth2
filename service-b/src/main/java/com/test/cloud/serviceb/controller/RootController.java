package com.test.cloud.serviceb.controller;

import com.test.cloud.serviceb.service.ServiceAService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("")
@RefreshScope
public class RootController {
    @Value("${test-value}")
    private String config;
    @Resource
    private ServiceAService serviceAService;
    @GetMapping("")
    public String index() {
        return "this is service b";
    }
    @GetMapping("a")
    public String getFromA() {
        return String.format("service b get [%s] from service a", serviceAService.getIndex());
    }
    @RequestMapping("config")
    public String getConfig() {
        return String.format("get config from git [%s]", config);
    }
}
