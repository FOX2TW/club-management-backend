package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.CommonResponse;
import com.tw.clubmanagement.controller.representation.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("ping")
    public CommonResult<String> ping() {
        return CommonResponse.success("pong");
    }
}
