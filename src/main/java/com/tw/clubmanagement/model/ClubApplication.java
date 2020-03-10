package com.tw.clubmanagement.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Builder
@Data
public class ClubApplication {
    private Integer id;
    private String name;
    private Integer type;
    private String picture;
    private String introduction;
    private Integer creatorId;
    private String creatorName;
    private Date applyDate;
}
