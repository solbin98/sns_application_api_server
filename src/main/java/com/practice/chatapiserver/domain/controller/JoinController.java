package com.practice.chatapiserver.domain.controller;

import com.practice.chatapiserver.domain.dto.JoinDto;
import com.practice.chatapiserver.domain.dto.ResponseDto;
import com.practice.chatapiserver.domain.service.JoinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {
    @Autowired
    private JoinService joinService;
    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody JoinDto joinDto) throws Exception{
        joinService.saveMember(joinDto);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK.value(), "회원가입에 성공했습니다.", joinDto), HttpStatus.OK);
    }
}
