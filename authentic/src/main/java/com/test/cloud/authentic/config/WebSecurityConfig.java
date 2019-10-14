package com.test.cloud.authentic.config;

import com.test.cloud.authentic.handler.CustomAuthenticFailHandler;
import com.test.cloud.authentic.handler.CustomAuthenticSuccessHandler;
import com.test.cloud.authentic.handler.FormAndJsonAuthenticationFilter;
import com.test.cloud.authentic.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserService userService;
    @Resource
    private CustomAuthenticSuccessHandler customAuthenticSuccessHandler;
    @Resource
    private CustomAuthenticFailHandler customAuthenticFailHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                    .permitAll()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/actuator/**", "/error").permitAll()
                    .antMatchers(HttpMethod.POST, "/user").permitAll()
                    .antMatchers(HttpMethod.POST,"/oauth/token").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilterAt(formAndJsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 必须配置加密
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public FormAndJsonAuthenticationFilter formAndJsonAuthenticationFilter() throws Exception {
        FormAndJsonAuthenticationFilter filter = new FormAndJsonAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(customAuthenticSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthenticFailHandler);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception { // 注册manager已启用password验证
//        return super.authenticationManagerBean();
//    }
}
