package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.controller.representation.ApplcationRecordCreateDTO;
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

    public ApplicationRecordEntity(ApplcationRecordCreateDTO recordCreateDTO) {
        this.setClubId(recordCreateDTO.getClubId());
        this.setUserId(recordCreateDTO.getUserId());
        this.setReason(recordCreateDTO.getReason());
        this.setCellphone(recordCreateDTO.getCellphone());
        this.setWeChatNo(recordCreateDTO.getWeChatNo());
    }

    public JoinApplicationDTO toJoinApplicationDTO() {
        return JoinApplicationDTO.builder()
                .clubId(clubId)
                .reason(reason)
                .applyDate(this.getCreatedAt())
                .build();
    }
}