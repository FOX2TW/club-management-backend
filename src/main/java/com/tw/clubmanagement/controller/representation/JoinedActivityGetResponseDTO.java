package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.model.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class JoinedActivityGetResponseDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String themePicture;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("manager")
    private Boolean manager;
    @JsonProperty("recruiting")
    private Boolean recruiting;

    public static List<JoinedActivityGetResponseDTO> fromActivityWithRole(List<Activity> joinedActivities,
                                                                          List<Integer> managedClubIds) {
        Set<Integer> clubIdSet = new HashSet<>(managedClubIds);

        return joinedActivities.stream().map(activity -> JoinedActivityGetResponseDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .themePicture(activity.getThemePicture())
                .description(activity.getDescription())
                .status(activity.getStatus())
                .manager(clubIdSet.contains(activity.getClubId()))
                .recruiting(activity.getStatus() == 0 && new Date().before(activity.getJoinEndTime()))
                .build()).collect(Collectors.toList());
    }
}
