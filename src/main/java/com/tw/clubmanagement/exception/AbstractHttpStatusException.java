package com.tw.clubmanagement.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public abstract class AbstractHttpStatusException extends RuntimeException {

  public AbstractHttpStatusException() {
    super();
  }

  public AbstractHttpStatusException(Throwable cause) {
    super(cause);
  }

  public AbstractHttpStatusException(String message) {
    super(message);
  }

  public AbstractHttpStatusException(Throwable cause, String messageTemplate, Object... args) {
    super(MessageFormat.format(messageTemplate, args), cause);
  }

  // Override this method to set the status for specific exception
  public abstract HttpStatus getStatus();

}
