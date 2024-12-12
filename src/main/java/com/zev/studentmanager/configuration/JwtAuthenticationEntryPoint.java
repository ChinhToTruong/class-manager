package com.zev.studentmanager.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.enums.MessageCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        MessageCode errorCode = MessageCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse.build()
                .withCode(errorCode.getCode())
                .withMessage(errorCode.getMessage());
        ApiResponse<?> apiResponse = ApiResponse
        .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
