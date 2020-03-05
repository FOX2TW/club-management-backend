package com.tw.clubmanagement.controller.representation;

import com.tw.clubmanagement.enums.ResultCode;

public class CommonResponse {
    private final static String SUCCESS = "success";

    private final static String FAIL = "fail";

    public static <T> CommonResult<T> success() {
        CommonResult<T> response = new CommonResult<>();
        response.setCode(ResultCode.SUCCESS.code);
        response.setMessage(SUCCESS);
        return response;
    }

    public static <T> CommonResult<T> success(String msg) {
        CommonResult<T> response = new CommonResult<>();
        response.setCode(ResultCode.SUCCESS.code);
        response.setMessage(msg);
        return response;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> response = new CommonResult<>();
        response.setCode(ResultCode.SUCCESS.code);
        response.setMessage(SUCCESS);
        response.setData(data);
        return response;
    }

    public static <T> CommonResult<T> fail() {
        CommonResult<T> response = new CommonResult<>();
        response.setCode(ResultCode.FAIL.code);
        response.setMessage(FAIL);
        return response;
    }

    public static <T> CommonResult<T> fail(String msg) {
        CommonResult<T> response = new CommonResult<>();
        response.setCode(ResultCode.FAIL.code);
        response.setMessage(msg);
        return response;
    }
}
