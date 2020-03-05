package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.controller.representation.UserInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user")
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    private String username;
    private String phone;
    private String password;
    private Integer status;
    private String profileImagePath;

    public UserInformation toUserInformation() {
        return UserInformation.builder()
                .username(username)
                .phone(phone)
                .password(password)
                .status(status)
                .profileImagePath(profileImagePath)
                .build();
    }
}