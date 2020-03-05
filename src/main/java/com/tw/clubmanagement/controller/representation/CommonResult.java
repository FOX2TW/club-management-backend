package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> {
    private T data;
    private int code;
    private String message;
}
