package com.tw.clubmanagement.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class ClubApplication {
    private Integer id;
    private String name;
    private Integer type;
    private String picture;
    private String introduction;
    private Date applyDate;
}
