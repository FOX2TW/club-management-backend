package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.model.UserInformation;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ClubDetailInfo {
    private Integer id;
    private String name;
    private Integer type;
    private String picture;
    private String introduction;
    private String address;
    private List<UserInformation> members;
    private List<Activity> activities;
    @JsonProperty("isManager")
    private boolean isManager;
    @JsonProperty("isJoin")
    private boolean isJoin;
    @JsonProperty("isApproved")
    private boolean isApproved;
    private Date createdAt;

}
