package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.model.ActivityParticipant;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_activity_participant")
@Builder
public class ActivityParticipantEntity extends BaseEntity {
    private Integer activityId;
    private Integer participantId;
    private Integer role;

    public ActivityParticipant toActivityParticipant() {
        return ActivityParticipant.builder()
                .id(id)
                .activityId(activityId)
                .participantId(participantId)
                .role(role)
                .build();
    }

    public static ActivityParticipantEntity fromActivityParticipant(ActivityParticipant activityParticipant) {
        ActivityParticipantEntity activityParticipantEntity = ActivityParticipantEntity.builder()
                .activityId(activityParticipant.getActivityId())
                .participantId(activityParticipant.getParticipantId())
                .role(activityParticipant.getRole())
                .build();

        activityParticipantEntity.setId(activityParticipant.getId());

        return activityParticipantEntity;
    }
}
