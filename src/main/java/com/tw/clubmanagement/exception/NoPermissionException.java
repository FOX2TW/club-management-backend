package com.tw.clubmanagement.exception;

import org.springframework.http.HttpStatus;

public class NoPermissionException extends AbstractHttpStatusException {

  public NoPermissionException() {
    super();
  }

  public NoPermissionException(Throwable cause) {
    super(cause);
  }

  public NoPermissionException(String message) {
    super(message);
  }

  public NoPermissionException(Throwable cause, String messageTemplate, Object... args) {
    super(cause, messageTemplate, args);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.FORBIDDEN;
  }
}
