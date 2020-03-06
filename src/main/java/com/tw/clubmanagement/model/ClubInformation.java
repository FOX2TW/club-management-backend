package com.tw.clubmanagement.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClubInformation {
    private Integer id;
    private String name;
    private Integer type;
    private String picture;
    private Boolean approveStatus;
    private Integer isManager;
    private String introduction;
}
