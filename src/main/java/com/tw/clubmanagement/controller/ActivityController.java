package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.InvolvedActivityGetResponseDTO;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    private static final Pattern POSITIVE_PATTERN = Pattern.compile("^[1-9]?[0-9]*$");
    private static final String USERID_INVALID_MESSAGE = "用户ID必须是一个正整数";

    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/user/{userId}")
    public List<InvolvedActivityGetResponseDTO> getInvolvedActivities(@PathVariable String userId) {
        if (!POSITIVE_PATTERN.matcher(userId).matches()) {
            throw new ValidationException(USERID_INVALID_MESSAGE);
        }

        return InvolvedActivityGetResponseDTO.fromActivities(
                activityService.getInvolvedActivities(Integer.valueOf(userId)));
    }
}
