package com.tw.clubmanagement.controller.representation;

import lombok.Data;

@Data
public class ClubRepresentation {
    private Integer id;
    private String name;
    private int type;
    private boolean approveStatus;
    private String introduction;
    private boolean deleteStatus;
}
