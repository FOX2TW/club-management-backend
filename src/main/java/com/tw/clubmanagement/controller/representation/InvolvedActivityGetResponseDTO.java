package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.model.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
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

    public static List<InvolvedActivityGetResponseDTO> fromActivities(List<Activity> activities) {
        return activities.stream().map(activity -> InvolvedActivityGetResponseDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .themePicture(activity.getThemePicture())
                .description(activity.getDescription())
                .status(activity.getStatus())
                .numberThumbsUp(activity.getNumberThumbsUp())
                .build()).collect(Collectors.toList());
    }
}
