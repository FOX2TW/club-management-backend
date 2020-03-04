package com.tw.clubmanagement.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractHttpStatusException {

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(Throwable cause, String messageTemplate, Object... args) {
    super(cause, messageTemplate, args);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
