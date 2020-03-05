package com.tw.clubmanagement.controller.representation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse {
    public Object data;
    public int code;
    public String message;
}
