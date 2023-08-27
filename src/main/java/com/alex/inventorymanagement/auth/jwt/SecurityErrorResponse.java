package com.alex.inventorymanagement.auth.jwt;

import com.alex.inventorymanagement.common.dto.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class SecurityErrorResponse {

    private final ObjectMapper objectMapper;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public SecurityErrorResponse(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, int status, String message) throws IOException {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(message)
                .details(request.getRequestURI())
                .build();

        response.setStatus(status);
        response.setContentType("application/json");
        objectMapper.setDateFormat(dateFormat);
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
