package com.tw.clubmanagement.controller.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.Activity;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

@Data
@Builder
public class ReleaseActivityPostRequestDTO {
    @JsonProperty("clubId")
    private Integer clubId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String themePicture;
    @JsonProperty("startDate")
    private Date startTime;
    @JsonProperty("endDate")
    private Date endTime;
    @JsonProperty("endJoinDate")
    private Date joinEndTime;
    @JsonProperty("limit")
    private Integer numberLimitation;
    @JsonProperty("description")
    private String description;
    @JsonProperty("open")
    private Boolean open;

    public Activity toActivity() {
        return Activity.builder()
                .clubId(clubId)
                .name(name)
                .themePicture(themePicture)
                .startTime(startTime)
                .endTime(endTime)
                .joinEndTime(joinEndTime)
                .numberLimitation(numberLimitation)
                .description(description)
                .status(0)
                .open(open ? 1 : 0)
                .numberThumbsUp(0)
                .build();
    }

    public void check() {
        if (StringUtils.isEmpty(name)) {
            throw new ValidationException("活动名称未填");
        }
        if (name.length() > 100) {
            throw new ValidationException("活动名称过长");
        }

        if (StringUtils.isEmpty(themePicture)) {
            throw new ValidationException("未指定活动主题图片");
        }
        if (themePicture.length() > 100) {
            throw new ValidationException("活动主题图片路径过长");
        }

        if (startTime.after(endTime)) {
            throw new ValidationException("活动起始时间不能在结束时间之后");
        }
        if (joinEndTime.after(startTime)) {
            throw new ValidationException("活动报名截止时间不能在起始时间之后");
        }
        Date now = new Date();
        if (startTime.before(now)) {
            throw new ValidationException("活动起始时间不能在当前时间之前");
        }
        if (joinEndTime.before(now)) {
            throw new ValidationException("活动报名截止时间不能在当前时间之前");
        }

        if (numberLimitation < 0) {
            throw new ValidationException("报名数量上限不能为负");
        }

        if (description != null && description.length() > 2000) {
            throw new ValidationException("活动描述过长");
        }

        if (open == null) {
            throw new ValidationException("未指定是否公开活动");
        }
    }
}
