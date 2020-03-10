package com.tw.clubmanagement.controller.representation;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JoinApplicationDTO {
    private Integer  clubId;
    private String clubName;
    private String applicantName;
    private Integer applicantId;
    private String reason;
    private Date applyDate;
}
