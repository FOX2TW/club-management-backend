package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.exception.InternalServerErrorException;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.model.ActivityParticipant;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class InvolvedActivityGetResponseDTO {
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
    @JsonProperty("thumbsUp")
    private Integer numberThumbsUp;
    @JsonProperty("role")
    private Integer role;

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

    public static List<InvolvedActivityGetResponseDTO> fromActivitiesWithParticipants(
            List<Activity> activities, List<ActivityParticipant> activityParticipants) {
        if (activityParticipants.size() != activities.size()) {
            throw new InternalServerErrorException("查询出现问题");
        }

        Map<Integer, Integer> activityIdRoleMap = activityParticipants.stream().collect(
                Collectors.toMap(ActivityParticipant::getActivityId, ActivityParticipant::getRole));

        return activities.stream().map(activity -> InvolvedActivityGetResponseDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .themePicture(activity.getThemePicture())
                .description(activity.getDescription())
                .status(status(activity.getJoinEndTime(), activity.getStartTime(), activity.getEndTime()))
                .numberThumbsUp(activity.getNumberThumbsUp())
                .role(activityIdRoleMap.get(activity.getId()))
                .build()).collect(Collectors.toList());
    }
}
