package com.test.cloud.gateway.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class CustomRedisRouteRepository implements RouteDefinitionRepository {
    @Resource
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return reactiveStringRedisTemplate.opsForHash().values("gateway").map(data -> {
            try {
                return objectMapper.readValue((String)data, RouteDefinition.class);
            } catch (IOException e) {
                return null;
            }
        });
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
