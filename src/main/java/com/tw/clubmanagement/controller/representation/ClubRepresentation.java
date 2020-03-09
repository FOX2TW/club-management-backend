package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

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
    @JsonIgnore
    private int createdBy;

    public ClubRepresentation isManager(Integer accessId) {
        this.isManager = accessId == createdBy;
        return this;
    }

    public ClubRepresentation isJoin(List<Integer> allJoinedClubIds) {
        if (CollectionUtils.isEmpty(allJoinedClubIds)){
            this.isJoin = false;
        } else if (!allJoinedClubIds.contains(this.id)) {
            this.isJoin = false;
        } else {
            this.isJoin = true;
        }
        return this;
    }
}
