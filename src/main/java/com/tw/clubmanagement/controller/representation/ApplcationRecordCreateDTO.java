package com.tw.clubmanagement.controller.representation;

import com.tw.clubmanagement.entity.ApplicationRecordEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplcationRecordCreateDTO {
    @NotNull(message = "俱乐部ID不能为空")
    private Integer clubId;
    private String reason;
    private String cellphone;
    private String weChatNo;

    public ApplicationRecordEntity toApplicationRecordEntity() {
        ApplicationRecordEntity applicationRecordEntity = new ApplicationRecordEntity();
        applicationRecordEntity.setClubId(this.getClubId());
        applicationRecordEntity.setCellphone(this.getCellphone());
        applicationRecordEntity.setWeChatNo(this.getWeChatNo());
        applicationRecordEntity.setReason(this.getReason());
        return applicationRecordEntity;
    }
}
