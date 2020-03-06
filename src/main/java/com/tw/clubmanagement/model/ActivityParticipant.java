package com.tw.clubmanagement.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ActivityParticipant {
    private Integer id;
    private Integer activityId;
    private Integer participantId;
    private Integer role;
}
