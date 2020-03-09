package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tw.clubmanagement.model.UserInformation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInformationGetResponseDTO {
    private Integer id;
    private String username;
    private Integer status;
    private String phone;
    private String email;
    private String profileImagePath;

    public static UserInformationGetResponseDTO fromUserInformation(UserInformation userInformation) {
        return UserInformationGetResponseDTO.builder()
                .id(userInformation.getId())
                .username(userInformation.getUsername())
                .phone(userInformation.getPhone())
                .email(userInformation.getEmail())
                .profileImagePath(userInformation.getProfileImagePath())
                .build();
    }
}
