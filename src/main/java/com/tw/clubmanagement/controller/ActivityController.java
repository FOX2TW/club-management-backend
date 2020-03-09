package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.ActivityDetailsGetResponseDTO;
import com.tw.clubmanagement.controller.representation.ActivityGetResponseDTO;
import com.tw.clubmanagement.controller.representation.InvolvedActivityGetResponseDTO;
import com.tw.clubmanagement.controller.representation.ReleaseActivityPostRequestDTO;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.model.ActivityParticipant;
import com.tw.clubmanagement.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<InvolvedActivityGetResponseDTO> getInvolvedActivities(
            @RequestHeader("currentUserId") Integer currentUserId, @PathVariable String userId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(userId).matches()) {
            throw new ValidationException(USERID_INVALID_MESSAGE);
        }
        if (!currentUserId.equals(Integer.valueOf(userId))) {
            throw new ValidationException("指定了非自己的用户ID");
        }

        List<ActivityParticipant> involvedActivityParticipants =
                activityService.getInvolvedActivityParticipants(Integer.valueOf(userId));
        List<Activity> involvedActivities = activityService.getInvolvedActivities(Integer.valueOf(userId));

        return InvolvedActivityGetResponseDTO.fromActivitiesWithParticipants(involvedActivities, involvedActivityParticipants);
    }

    @GetMapping
    public List<ActivityGetResponseDTO> getActivities(@RequestHeader("currentUserId") Integer currentUserId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }

        return ActivityGetResponseDTO.fromActivities(activityService.getActivities());
    }

    @GetMapping("/{activityId}")
    public ActivityDetailsGetResponseDTO getActivityById(
            @RequestHeader("currentUserId") Integer currentUserId, @PathVariable String activityId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是个正整数");
        }

        Activity activity = activityService.getActivityById(Integer.valueOf(activityId));
        List<Integer> participantIds = activityService.getParticipantIds(Integer.valueOf(activityId));

        return ActivityDetailsGetResponseDTO.fromActivityAndJoinedUsers(activity, participantIds);
    }

    @GetMapping("/club/{clubId}")
    public List<ActivityGetResponseDTO> getActivitiesByClubId(
            @RequestHeader("currentUserId") Integer currentUserId, @PathVariable String clubId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(clubId).matches()) {
            throw new ValidationException("俱乐部ID必须是个正整数");
        }

        List<Activity> activities = activityService.getActivitiesByClubId(Integer.valueOf(clubId));

        return ActivityGetResponseDTO.fromActivities(activities);
    }

    @DeleteMapping("/{activityId}")
    public void deleteActivityById(
            @RequestHeader("currentUserId") Integer currentUserId, @PathVariable String activityId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是个正整数");
        }

        activityService.deleteActivityById(Integer.valueOf(activityId));
    }

    @PostMapping
    public Integer releaseActivity(@RequestHeader("currentUserId") Integer currentUserId,
                                   @RequestBody ReleaseActivityPostRequestDTO releaseActivityPostRequestDTO) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        releaseActivityPostRequestDTO.check();

        Activity activity = releaseActivityPostRequestDTO.toActivity();
        return activityService.releaseActivity(currentUserId, activity);
    }
}
