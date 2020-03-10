package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.model.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ActivityDetailsGetResponseDTO {
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
    @JsonProperty("startDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonProperty("endDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JsonProperty("endJoinDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinEndTime;
    @JsonProperty("limit")
    private Integer numberLimitation;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("open")
    private Integer open;
    @JsonProperty("thumbsUp")
    private Integer numberThumbsUp;

    @JsonProperty("joinedUser")
    private List<Integer> joinedUsers;
    @JsonProperty("manager")
    private Boolean manager;
    @JsonProperty("recruiting")
    private Boolean recruiting;
    @JsonProperty("joined")
    private Boolean joined;
    @JsonProperty("memberVisible")
    private Boolean memberVisible;

    public static ActivityDetailsGetResponseDTO from(Activity activity,
                                                     List<Integer> participantIds,
                                                     List<Integer> managedClubIds,
                                                     List<Integer> joinedActivityIds,
                                                     List<Integer> clubIds,
                                                     Map<Integer, String> clubIdNameMap) {
        return ActivityDetailsGetResponseDTO.builder()
                .id(activity.getId())
                .clubId(activity.getClubId())
                .clubName(clubIdNameMap.get(activity.getClubId()))
                .name(activity.getName())
                .themePicture(activity.getThemePicture())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .joinEndTime(activity.getJoinEndTime())
                .numberLimitation(activity.getNumberLimitation())
                .description(activity.getDescription())
                .status(activity.getStatus())
                .open(activity.getOpen())
                .numberThumbsUp(activity.getNumberThumbsUp())
                .joinedUsers(participantIds)
                .manager(managedClubIds.contains(activity.getClubId()))
                .recruiting(activity.getStatus() == 0 && new Date().before(activity.getJoinEndTime()))
                .joined(joinedActivityIds.contains(activity.getId()))
                .memberVisible(clubIds.contains(activity.getClubId()))
                .build();
    }
}
