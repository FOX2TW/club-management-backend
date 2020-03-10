package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.model.ClubApplication;
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
    private String picture;
    private String address;
    private boolean deleteStatus;
    private boolean processStatus;

    public ClubInformation toClubInformation() {
        return ClubInformation.builder()
                .id(id)
                .name(name)
                .type(type)
                .picture(picture)
                .approveStatus(approveStatus)
                .introduction(introduction)
                .build();
    }

    public ClubApplication toClubApplication() {
        return ClubApplication.builder()
                .id(id)
                .name(name)
                .type(type)
                .picture(picture)
                .introduction(introduction)
                .creatorId(this.getCreatedBy())
                .applyDate(this.getCreatedAt())
                .build();
    }
}
