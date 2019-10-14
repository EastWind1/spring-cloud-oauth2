package com.test.cloud.servicec.config;

import com.test.cloud.servicec.handler.ServiceCHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction indexConfig(ServiceCHandler serviceCHandler) {
        return RouterFunctions.route(RequestPredicates.GET(""), serviceCHandler::index);
    }
}
