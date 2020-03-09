package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.controller.representation.MemberInfo;
import com.tw.clubmanagement.model.ClubMember;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_club_member")
public class ClubMemberEntity extends BaseEntity {
    private Integer userId;
    private Integer clubId;
    private boolean managerFlag;

    public ClubMember toClubMember() {
        return ClubMember.builder()
                .id(id)
                .userId(userId)
                .clubId(clubId)
                .isManager(managerFlag)
                .build();
    }
    public MemberInfo toMemberInfo() {
        return MemberInfo.builder()
                .id(userId)
                .username("aaa") // TODO
                .profileImagePath("www.baidu.com") // TODO
                .build();
    }
}