package com.test.cloud.authentic.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAuthenticSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        HashMap<String, Object> result = new HashMap<>();
        result.put("status", HttpStatus.OK);
        result.put("message", "Authentication Successful");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
