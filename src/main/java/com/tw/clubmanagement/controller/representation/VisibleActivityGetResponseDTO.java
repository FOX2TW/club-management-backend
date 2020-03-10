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
public class VisibleActivityGetResponseDTO {
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

    @JsonProperty("thumbsUp")
    private Integer numberThumbsUp;

    @JsonProperty("joined")
    private Boolean joined;
    @JsonProperty("memberVisible")
    private Boolean memberVisible;
    @JsonProperty("open")
    private Boolean open;

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

    public static List<VisibleActivityGetResponseDTO> fromActivitiesWithRole(List<Activity> joinedActivities,
                                                                             List<Activity> memberVisibleActivities,
                                                                             List<Activity> openActivities,
                                                                             List<Integer> managedClubIds,
                                                                             Map<Integer, String> clubIdNameMap) {
        Set<Integer> clubIdSet = new HashSet<>(managedClubIds);
        Map<Integer, Integer> memberVisibleActivityIdMap =
                memberVisibleActivities.stream().collect(Collectors.toMap(Activity::getId, Activity::getId));

        Map<Integer, VisibleActivityGetResponseDTO> idVisibleMap =
                joinedActivities.stream().map(activity -> VisibleActivityGetResponseDTO.builder()
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
                                .numberThumbsUp(activity.getNumberThumbsUp())
                                .joined(true)
                                .memberVisible(memberVisibleActivityIdMap.containsKey(activity.getId()))
                                .open(activity.getOpen() == 1)
                                .build()).collect(Collectors.toMap(
                        VisibleActivityGetResponseDTO::getId,
                        visibleActivityGetResponseDTO -> visibleActivityGetResponseDTO));

        memberVisibleActivities.forEach(activity -> idVisibleMap.putIfAbsent(activity.getId(),
                VisibleActivityGetResponseDTO.builder()
                        .id(activity.getId())
                        .clubId(activity.getClubId())
                        .clubName(clubIdNameMap.get(activity.getClubId()))
                        .name(activity.getName())
                        .themePicture(activity.getThemePicture())
                        .description(activity.getDescription())
                        .status(activity.getStatus())
                        .manager(clubIdSet.contains(activity.getClubId()))
                        .recruiting(activity.getStatus() == 0 && new Date().before(activity.getJoinEndTime()))
                        .joinEndTime(activity.getJoinEndTime())
                        .numberThumbsUp(activity.getNumberThumbsUp())
                        .joined(false)
                        .memberVisible(true)
                        .open(activity.getOpen() == 1)
                        .build()));

        openActivities.forEach(activity -> idVisibleMap.putIfAbsent(activity.getId(),
                VisibleActivityGetResponseDTO.builder()
                        .id(activity.getId())
                        .clubId(activity.getClubId())
                        .clubName(clubIdNameMap.get(activity.getClubId()))
                        .name(activity.getName())
                        .themePicture(activity.getThemePicture())
                        .description(activity.getDescription())
                        .status(activity.getStatus())
                        .manager(clubIdSet.contains(activity.getClubId()))
                        .recruiting(activity.getStatus() == 0 && new Date().before(activity.getJoinEndTime()))
                        .joinEndTime(activity.getJoinEndTime())
                        .numberThumbsUp(activity.getNumberThumbsUp())
                        .joined(false)
                        .memberVisible(false)
                        .open(true)
                        .build()));

        return new ArrayList<>(idVisibleMap.values());
    }
}
