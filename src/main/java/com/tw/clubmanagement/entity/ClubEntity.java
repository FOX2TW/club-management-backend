package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.model.ClubInformation;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_club")
public class ClubEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private int type;
    private boolean approveStatus;
    private String introduction;
    private boolean deleteStatus;

    public ClubInformation toClubInformation() {
        return ClubInformation.builder()
                .id(id)
                .name(name)
                .type(type)
                .approveStatus(approveStatus)
                .introduction(introduction)
                .build();
    }
}
