package com.tw.clubmanagement.entity;

import com.tw.clubmanagement.model.ActivityParticipant;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_activity_participant")
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
}
