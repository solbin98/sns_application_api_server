package com.practice.chatapiserver.security.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.chatapiserver.domain.dto.ErrorResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthenticationExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (Exception e){
            log.error(e.getMessage());

            ErrorResponseDto error = new ErrorResponseDto();
            error.setMessage(e.getMessage());
            error.setStatusCode(HttpStatus.BAD_REQUEST.value());

            ObjectMapper objectToJsonConverter = new ObjectMapper();
            String errorString = objectToJsonConverter.writeValueAsString(error);

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().println(errorString);
        }
    }
}
