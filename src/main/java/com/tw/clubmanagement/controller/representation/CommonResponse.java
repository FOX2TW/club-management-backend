package com.tw.clubmanagement.controller.representation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private T data;
    private int code;
    private String message;
}
