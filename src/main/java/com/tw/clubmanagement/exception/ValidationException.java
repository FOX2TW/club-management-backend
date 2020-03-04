package com.tw.clubmanagement.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends AbstractHttpStatusException {

  public ValidationException() {
    super();
  }

  public ValidationException(Throwable cause) {
    super(cause);
  }

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(Throwable cause, String messageTemplate, Object... args) {
    super(cause, messageTemplate, args);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
