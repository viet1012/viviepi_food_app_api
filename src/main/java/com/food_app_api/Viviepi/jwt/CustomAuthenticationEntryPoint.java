package com.food_app_api.Viviepi.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food_app_api.Viviepi.payload.response.ResponseError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        ResponseError responseError = new ResponseError();
        responseError.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        responseError.setMessage("Invalid credentials");
        mapper.writeValue(responseStream, responseError);
        responseStream.flush();
    }
}
