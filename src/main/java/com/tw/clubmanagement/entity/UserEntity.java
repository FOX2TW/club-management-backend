package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.model.UserInformation;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class UserEntity extends BaseEntity {
    private Integer id;
    private String username;
    private String email;
    private Integer status;
    private String phone;
    private String profileImagePath;

    public UserInformation toUserInformation() {
        return UserInformation.builder()
                .id(id)
                .username(username)
                .email(email)
                .phone(phone)
                .profileImagePath(profileImagePath)
                .build();
    }
}