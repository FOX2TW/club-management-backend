package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.model.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ActivityDetailsGetResponseDTO {
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
    @JsonProperty("joinedUser")
    private List<Integer> joinedUsers;


    public static ActivityDetailsGetResponseDTO fromActivityAndJoinedUsers(Activity activity, List<Integer> joinedUsers) {
        return ActivityDetailsGetResponseDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .themePicture(activity.getThemePicture())
                .description(activity.getDescription())
                .numberThumbsUp(activity.getNumberThumbsUp())
                .joinedUsers(joinedUsers)
                .build();
    }
}
