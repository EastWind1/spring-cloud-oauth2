package com.test.cloud.serviceb.service;

import com.test.cloud.serviceb.service.impl.ServiceAServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-a", fallback = ServiceAServiceHystrix.class)
public interface ServiceAService {
    @GetMapping("")
    String getIndex();
}
