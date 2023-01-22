package com.practice.chatapiserver.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponseDto {
    int statusCode;
    String message;
    List<FieldError> errorList = new ArrayList<>();
}
