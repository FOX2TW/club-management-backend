package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.controller.representation.ApplcationRecordCreateDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "t_application_record")
public class ApplicationRecordEntity extends BaseEntity {
    private Integer userId;
    private Integer clubId;
    private String reason;
    private String cellphone;
    private String weChatNo;

    public ApplicationRecordEntity(ApplcationRecordCreateDTO recordCreateDTO) {
        this.setClubId(recordCreateDTO.getClubId());
        this.setUserId(recordCreateDTO.getUserId());
        this.setReason(recordCreateDTO.getReason());
        this.setCellphone(recordCreateDTO.getCellphone());
        this.setWeChatNo(recordCreateDTO.getWeChatNo());
    }
}