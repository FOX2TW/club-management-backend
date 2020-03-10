package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.model.ClubMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_club_member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static ClubMemberEntity fromClubMember(ClubMember clubMember) {
        return ClubMemberEntity.builder()
                .userId(clubMember.getUserId())
                .clubId(clubMember.getClubId())
                .managerFlag(clubMember.getIsManager())
                .build();
    }
}