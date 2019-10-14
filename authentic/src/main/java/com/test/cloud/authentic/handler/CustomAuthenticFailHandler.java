package com.test.cloud.authentic.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAuthenticFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");

        HashMap<String, Object> result = new HashMap<>();
        result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        result.put("message", "Authentication Failed");
        result.put("detail", exception.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
