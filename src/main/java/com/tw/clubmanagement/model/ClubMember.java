package com.tw.clubmanagement.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClubMember {
    private Integer id;
    private Integer userId;
    private Integer clubId;
    private Boolean isManager;
}
