package com.farukcankaya.springcase.common;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MissingParameterException.class)
  public final ResponseEntity<ErrorResponse> handleMissingParameterException(
      MissingParameterException ex) {
    ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List errors = Collections.singletonList(ex.getMostSpecificCause().getLocalizedMessage());
    return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List errors = prepareMethodArgumentNotValidErrors(ex.getBindingResult());
    return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
  }

  private List<String> prepareMethodArgumentNotValidErrors(BindingResult bindingResult) {
    List<String> fieldErrors =
        bindingResult
            .getFieldErrors()
            .stream()
            .map(
                fieldError ->
                    new StringBuilder()
                        .append(fieldError.getField())
                        .append(" ")
                        .append(fieldError.getDefaultMessage())
                        .append(" but ")
                        .append(fieldError.getRejectedValue())
                        .append(" is sent")
                        .toString())
            .collect(Collectors.toList());
    List<String> globalErrors =
        bindingResult
            .getGlobalErrors()
            .stream()
            .map(
                error ->
                    new StringBuilder()
                        .append(error.getObjectName())
                        .append(" ")
                        .append(error.getDefaultMessage())
                        .toString())
            .collect(Collectors.toList());

    return Stream.concat(fieldErrors.stream(), globalErrors.stream()).collect(Collectors.toList());
  }
}
