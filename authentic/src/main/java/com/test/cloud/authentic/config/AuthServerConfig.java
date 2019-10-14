package com.test.cloud.authentic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
//    @Resource
//    private AuthenticationManager authenticationManagerBean;
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in-memory存储
                .withClient("client") // client_id
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .secret("test")
                .scopes("server")
                    .redirectUris("http://localhost:4200/login", "http://localhost:8000/service-b/")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenServices(tokenServices());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // secret不加密
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients(); // 启用表单提交client_id client_secret进行认证，否则使用Basic认证
    }
    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(new RedisTokenStore(redisConnectionFactory));
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(30));
        defaultTokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
        return defaultTokenServices;
    }
}
