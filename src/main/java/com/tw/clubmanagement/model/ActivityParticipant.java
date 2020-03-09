package com.tw.clubmanagement.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ActivityParticipant {
    private Integer id;
    private Integer activityId;
    private Integer participantId;
    private Integer role;
}
