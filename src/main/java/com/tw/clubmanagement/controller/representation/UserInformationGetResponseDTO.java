package com.tw.clubmanagement.controller.representation;

import com.tw.clubmanagement.model.UserInformation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInformationGetResponseDTO {
    private Integer id;
    private String username;
    private Integer status;
    private String phone;
    private String profileImagePath;

    public static UserInformationGetResponseDTO fromUserInformation(UserInformation userInformation) {
        return UserInformationGetResponseDTO.builder()
                .id(userInformation.getId())
                .username(userInformation.getUsername())
                .status(userInformation.getStatus())
                .phone(userInformation.getPhone())
                .profileImagePath(userInformation.getProfileImagePath())
                .build();
    }
}
