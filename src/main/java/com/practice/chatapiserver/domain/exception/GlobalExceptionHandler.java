package com.practice.chatapiserver.domain.exception;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.domain.dto.ErrorResponseDto;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) // 객체 검증 오류 핸들러
    public ResponseEntity<ErrorResponseDto> methodArgumentHandler(MethodArgumentNotValidException e, BindingResult bindingResult){
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(e.getMessage());
        error.setErrorList(bindingResult.getFieldErrors());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class, NoResultException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDto> customMessageErrorExceptionHandler(Exception e){
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(e.getMessage());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exceptionHandle(Exception e){
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(ErrorCodeEnum.SERVER_INNER_ERROR.getMsg());
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
