package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.model.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ActivityGetResponseDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String themePicture;
    @JsonProperty("description")
    private String description;
    @JsonProperty("thumbsUp")
    private Integer numberThumbsUp;

    public static List<ActivityGetResponseDTO> fromActivities(List<Activity> activities) {
        return activities.stream().map(ActivityGetResponseDTO::fromActivity).collect(Collectors.toList());
    }

    private static ActivityGetResponseDTO fromActivity(Activity activity) {
        return ActivityGetResponseDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .themePicture(activity.getThemePicture())
                .description(activity.getDescription())
                .numberThumbsUp(activity.getNumberThumbsUp())
                .build();
    }
}
