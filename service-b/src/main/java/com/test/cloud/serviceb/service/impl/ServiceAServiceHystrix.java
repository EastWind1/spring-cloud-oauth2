package com.test.cloud.serviceb.service.impl;

import com.test.cloud.serviceb.service.ServiceAService;
import org.springframework.stereotype.Service;

@Service
public class ServiceAServiceHystrix implements ServiceAService {
    @Override
    public String getIndex() {
        return "service a is fail. this is fail fallback";
    }
}
