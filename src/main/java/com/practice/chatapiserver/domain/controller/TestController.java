package com.practice.chatapiserver.domain.controller;

import com.practice.chatapiserver.domain.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<ResponseDto> test(HttpServletRequest request){
        log.info((String)request.getAttribute("member_id"));
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), "테스트 성공"), HttpStatus.OK);
    }
}
