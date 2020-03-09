package com.tw.clubmanagement.controller.representation;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ClubDetailInfo {
    private String name;
    private Integer type;
    private String picture;
    private String introduction;
    private String address;
    private List<MemberInfo> members;
    private List<ActivityInfo> activities;
    private Date createdAt;

}
