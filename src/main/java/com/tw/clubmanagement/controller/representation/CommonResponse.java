package com.tw.clubmanagement.controller.representation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse {
    private Object data;
    private int code;
    private String message;
}
