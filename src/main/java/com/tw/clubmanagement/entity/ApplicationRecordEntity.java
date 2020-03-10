package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.controller.representation.JoinApplicationDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "t_application_record")
public class ApplicationRecordEntity extends BaseEntity {
    public static int UNPROCESSED = 0;
    public static int AGREED = 1;
    public static int REJECTED = 2;

    private Integer userId;
    private Integer clubId;
    private String reason;
    private String cellphone;
    private String weChatNo;
    private String managerComment;
    private int status;

    public JoinApplicationDTO toJoinApplicationDTO() {
        return JoinApplicationDTO.builder()
                .recordId(this.getId())
                .clubId(clubId)
                .reason(reason)
                .applicantId(userId)
                .applyDate(this.getCreatedAt())
                .build();
    }
}