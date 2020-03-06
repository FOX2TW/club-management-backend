package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.model.ClubMember;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_club_member")
public class ClubMemberEntity extends BaseEntity {
    private Integer id;
    private Integer userId;
    private Integer clubId;
    private Integer managerFlag;

    public ClubMember toClubMember() {
        return ClubMember.builder()
                .id(id)
                .userId(userId)
                .clubId(clubId)
                .isManager(managerFlag == 1)
                .build();
    }
}