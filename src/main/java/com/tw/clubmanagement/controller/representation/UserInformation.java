package com.tw.clubmanagement.controller.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInformation {
    private String username;
    private String phone;
    private String password;
    private Integer status;
    private String profileImagePath;
}
