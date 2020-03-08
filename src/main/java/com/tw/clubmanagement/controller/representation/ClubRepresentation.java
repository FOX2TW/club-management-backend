package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClubRepresentation {
    private Integer id;
    private String name;
    private int type;
    private boolean approveStatus;
    @JsonProperty("isManager")
    private boolean isManager;
    @JsonProperty("isJoin")
    private boolean isJoin;
    private String introduction;
}
