package com.tw.clubmanagement.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AbstractHttpStatusException {

  public InternalServerErrorException() {
    super();
  }

  public InternalServerErrorException(Throwable cause) {
    super(cause);
  }

  public InternalServerErrorException(String message) {
    super(message);
  }

  public InternalServerErrorException(Throwable cause, String messageTemplate, Object... args) {
    super(cause, messageTemplate, args);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
