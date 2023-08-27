package com.alex.inventorymanagement.auth.jwt;

import com.alex.inventorymanagement.common.dto.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
//        authException.printStackTrace();
//        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        sendErrorResponse(response, request, HttpServletResponse.SC_BAD_REQUEST);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpServletRequest request, int status) throws IOException {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message("Resource not found")
                .details(request.getRequestURI())
                .build();

        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
    }
}
