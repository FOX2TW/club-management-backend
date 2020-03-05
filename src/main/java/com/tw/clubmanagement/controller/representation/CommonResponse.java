package com.tw.clubmanagement.controller.representation;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private T data;
    private int code;
    private String message;
}
