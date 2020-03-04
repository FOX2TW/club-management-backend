package com.tw.clubmanagement.exception.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class HttpStatusExceptionRepresentation {

  private Long timestamp;
  private Integer statusCode;
  private String statusDescription;
  private String message;
  private String path;
  private String cause;
  private String hash;

}
