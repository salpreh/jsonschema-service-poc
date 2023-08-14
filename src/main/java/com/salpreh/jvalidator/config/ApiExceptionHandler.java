package com.salpreh.jvalidator.config;

import com.salpreh.jvalidator.exceptions.SchemaValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ApiExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(SchemaValidationException.class)
  public ResponseEntity<ErrorMessage> handleException(SchemaValidationException e) {
    return ResponseEntity.badRequest()
        .body(new ErrorMessage(e.getMessage()));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleException(Exception e) {
    return ResponseEntity.internalServerError()
        .body(new ErrorMessage(e.getMessage()));
  }

  public record ErrorMessage(String message) { }
}