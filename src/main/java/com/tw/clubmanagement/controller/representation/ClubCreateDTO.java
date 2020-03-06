package com.tw.clubmanagement.controller.representation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClubCreateDTO {
    @NotBlank(message = "俱乐部名字不能为空")
    private String name;
    @NotNull(message = "俱乐部类型不能为空")
    private Integer type;
    private String picture;
    @NotBlank(message = "俱乐部介绍不能为空")
    private String introduction;
    private String address;
}
