package com.farukcankaya.springcase.common;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ErrorResponse> handleNotFoundExceptionHandler(NotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
