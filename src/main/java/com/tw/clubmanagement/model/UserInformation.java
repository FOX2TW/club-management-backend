package com.tw.clubmanagement.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInformation {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String profileImagePath;
}
