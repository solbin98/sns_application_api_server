package com.practice.chatapiserver.domain.service;

import com.practice.chatapiserver.domain.dto.LoginDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoginService {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    public Authentication validateLoginDto(LoginDto loginDto) throws AuthenticationException, Exception {
        try{
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
            return authentication;
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("비밀번호가 잘못되었습니다.");
        }
    }
}
