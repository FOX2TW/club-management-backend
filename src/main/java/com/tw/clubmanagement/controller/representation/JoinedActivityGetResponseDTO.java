package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.model.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
public class JoinedActivityGetResponseDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("clubId")
    private Integer clubId;
    @JsonProperty("clubName")
    private String clubName;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("joinEndTime")
    private Date joinEndTime;

    private static Integer status(Date joinEndTime, Date start, Date end) {
        Date now = new Date();
        if (joinEndTime != null && now.before(joinEndTime)) {
            return 0;
        }
        if (start != null && now.before(start)) {
            return 1;
        }
        if (end != null && now.before(end)) {
            return 2;
        }

        return 3;
    }

    public static List<JoinedActivityGetResponseDTO> fromActivityWithRole(List<Activity> joinedActivities,
                                                                          List<Integer> managedClubIds,
                                                                          Map<Integer, String> clubIdNameMap) {
        Set<Integer> clubIdSet = new HashSet<>(managedClubIds);

        return joinedActivities.stream().map(activity -> JoinedActivityGetResponseDTO.builder()
                .id(activity.getId())
                .clubId(activity.getClubId())
                .clubName(clubIdNameMap.get(activity.getClubId()))
                .name(activity.getName())
                .themePicture(activity.getThemePicture())
                .description(activity.getDescription())
                .status(status(activity.getJoinEndTime(), activity.getStartTime(), activity.getEndTime()))
                .manager(clubIdSet.contains(activity.getClubId()))
                .recruiting(activity.getStatus() == 0 && new Date().before(activity.getJoinEndTime()))
                .joinEndTime(activity.getJoinEndTime())
                .build()).collect(Collectors.toList());
    }
}
