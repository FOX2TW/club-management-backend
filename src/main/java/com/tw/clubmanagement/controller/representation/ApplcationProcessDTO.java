package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplcationProcessDTO {
    @NotNull(message = "申请记录ID不能为空")
    private Integer recordId;
    @NotNull(message = "处理结果不能唯恐")
    @JsonProperty("isAgree")
    private boolean agree;
    private String managerComment;
}

