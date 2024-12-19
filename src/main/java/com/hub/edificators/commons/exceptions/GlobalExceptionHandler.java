package com.hub.edificators.commons.exceptions;

import com.hub.edificators.commons.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public GenericResponse handleBusinessException(BusinessException ex) {
          return GenericResponse.createErrorResponse(ex.getMessage(), null, ex.getErrorCode());
    }
}
