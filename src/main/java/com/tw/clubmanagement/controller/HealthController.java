package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("ping")
    public CommonResponse ping() {
        CommonResponse response = CommonResponse.builder()
                .code(200)
                .data("pong").build();
        return response;
    }
}
