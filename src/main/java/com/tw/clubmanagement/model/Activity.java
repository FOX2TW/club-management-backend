package com.tw.clubmanagement.model;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Activity {
    private Integer id;
    private Integer clubId;
    private String name;
    private String themePicture;
    private Date startTime;
    private Date endTime;
    private Date joinEndTime;
    private Integer numberJoined;
    private Integer numberLimitation;
    private String description;
    private Integer status;
    private Integer open;
    private Integer numberThumbsUp;
}
