package com.practice.chatapiserver.domain.controller;

import com.practice.chatapiserver.domain.dto.LoginDto;
import com.practice.chatapiserver.domain.dto.ResponseDto;
import com.practice.chatapiserver.security.authenticate.jwt.JwtTokenManager;
import com.practice.chatapiserver.domain.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) throws Exception{
        Authentication authentication = loginService.validateLoginDto(loginDto);
        String accessToken = JwtTokenManager.createAccessToken(authentication);
        return new ResponseEntity<ResponseDto>(new ResponseDto(HttpStatus.OK.value(), "accessToken creation success", accessToken), HttpStatus.OK);
    }
}
