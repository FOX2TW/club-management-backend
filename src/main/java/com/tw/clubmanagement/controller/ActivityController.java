package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.application.ActivityFacadeService;
import com.tw.clubmanagement.controller.representation.*;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {
    private static final Pattern POSITIVE_PATTERN = Pattern.compile("^[1-9]?[0-9]*$");

    private ActivityService activityService;
    private ActivityFacadeService activityFacadeService;

    @Autowired
    public ActivityController(ActivityService activityService,
                              ActivityFacadeService activityFacadeService) {
        this.activityService = activityService;
        this.activityFacadeService = activityFacadeService;
    }

    @PostMapping
    public Integer releaseActivity(@RequestHeader("currentUserId") Integer currentUserId,
                                   @RequestBody ReleaseActivityPostRequestDTO releaseActivityPostRequestDTO) {
        log.info("Current user id: " + currentUserId);
        log.info("request: " + releaseActivityPostRequestDTO);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        releaseActivityPostRequestDTO.check();

        Activity activity = releaseActivityPostRequestDTO.toActivity();
        Integer activityId = activityService.releaseActivity(currentUserId, activity);
        log.info("Released activity: " + activityId);
        return activityId;
    }

    @PutMapping
    public void editActivity(@RequestHeader("currentUserId") Integer currentUserId,
                                   @RequestBody ActivityUpdateRequestDTO activityUpdateRequestDTO) {
        log.info("Current user id: " + currentUserId);
        log.info("request: " + activityUpdateRequestDTO);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        activityUpdateRequestDTO.check();

        Activity activity = activityUpdateRequestDTO.toActivity();
        activityService.updateActivity(currentUserId, activity);
    }

    @DeleteMapping("/{activityId}")
    public void deleteActivityById(@RequestHeader("currentUserId") Integer currentUserId,
                                   @PathVariable String activityId) {
        log.info("Current user id: " + currentUserId);
        log.info("Activity id: " + activityId);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是一个正整数");
        }

        activityService.deleteActivityById(currentUserId, Integer.valueOf(activityId));
    }

    @GetMapping("/list/joined")
    public List<JoinedActivityGetResponseDTO> getJoinedActivities(
            @RequestHeader("currentUserId") Integer currentUserId) {
        log.info("Current user id: " + currentUserId);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }

        List<JoinedActivityGetResponseDTO> joinedActivities = activityFacadeService.getJoinedActivities(currentUserId);
        log.info("Joined activities: " + joinedActivities);
        return joinedActivities;
    }

    @GetMapping("/list/visible")
    public List<VisibleActivityGetResponseDTO> getVisibleActivities(
            @RequestHeader("currentUserId") Integer currentUserId) {
        log.info("Current user id: " + currentUserId);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }

        List<VisibleActivityGetResponseDTO> visibleActivities = activityFacadeService.getVisibleActivities(currentUserId);
        log.info("Visible activities: " + visibleActivities);
        return visibleActivities;
    }

    @GetMapping("/{activityId}")
    public ActivityDetailsGetResponseDTO getActivityDetailsById(@RequestHeader("currentUserId") Integer currentUserId,
                                                                @PathVariable String activityId) {
        log.info("Current user id: " + currentUserId);
        log.info("Activity id: " + activityId);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是一个正整数");
        }

        ActivityDetailsGetResponseDTO activityDetails =
                activityFacadeService.getActivityDetailsById(currentUserId, Integer.valueOf(activityId));

        log.info("Activity details: " + activityDetails);
        return activityDetails;
    }

    @GetMapping("/club/{clubId}")
    public List<VisibleActivityGetResponseDTO> getVisibleActivitiesByClubId(
            @RequestHeader("currentUserId") Integer currentUserId, @PathVariable String clubId) {
        log.info("Current user id: " + currentUserId);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(clubId).matches()) {
            throw new ValidationException("俱乐部ID必须是一个正整数");
        }

        List<VisibleActivityGetResponseDTO> visibleActivityByClub = activityFacadeService.getVisibleActivities(currentUserId).stream()
                .filter(visibleActivity -> visibleActivity.getClubId().equals((Integer.valueOf(clubId))))
                .collect(Collectors.toList());

        log.info("Visible activity: " + visibleActivityByClub);
        return visibleActivityByClub;
    }

    @PutMapping("/{activityId}/join")
    public void joinActivity(@RequestHeader("currentUserId") Integer currentUserId, @PathVariable String activityId) {
        log.info("Current user id: " + currentUserId);
        log.info("Activity id: " + activityId);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是一个正整数");
        }

        activityService.joinActivity(currentUserId, Integer.valueOf(activityId));
    }

    @PutMapping("/{activityId}/cancelJoining")
    public void cancelJoiningActivity(@RequestHeader("currentUserId") Integer currentUserId, @PathVariable String activityId) {
        log.info("Current user id: " + currentUserId);
        log.info("Activity id: " + activityId);
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是一个正整数");
        }

        activityService.cancelJoiningActivity(currentUserId, Integer.valueOf(activityId));
    }

    @PutMapping("/{activityId}/thumbsUp")
    public void thumbsUpActivity(@RequestHeader("currentUserId") Integer currentUserId, @PathVariable String activityId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是一个正整数");
        }

        activityService.thumbsUpActivity(currentUserId, Integer.valueOf(activityId));
    }

    @PutMapping("/{activityId}/cancelThumbsUp")
    public void cancelThumbsUpActivity(@RequestHeader("currentUserId") Integer currentUserId, @PathVariable String activityId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是一个正整数");
        }

        activityService.cancelThumbsUpActivity(currentUserId, Integer.valueOf(activityId));
    }
}
