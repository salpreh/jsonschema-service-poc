package com.salpreh.jvalidator.config;

import com.salpreh.jvalidator.exceptions.NotFoundException;
import com.salpreh.jvalidator.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ApiExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorMessage> handleException(NotFoundException e) {
    return ResponseEntity.notFound()
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorMessage> handleException(ValidationException e) {
    return ResponseEntity.badRequest()
        .body(ErrorMessage.of(e));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleException(Exception e) {
    return ResponseEntity.internalServerError()
        .body(ErrorMessage.of(e));
  }

  public record ErrorMessage(String message) {
    public static ErrorMessage of(Throwable t) {
      return new ErrorMessage(t.getMessage());
    }
  }
}