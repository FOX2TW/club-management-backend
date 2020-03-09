package com.tw.clubmanagement.controller.representation;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberInfo {
    private Integer id;
    private String username;
    private String profileImagePath;
}
