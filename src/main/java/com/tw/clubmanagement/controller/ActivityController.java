package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.application.ActivityFacadeService;
import com.tw.clubmanagement.controller.representation.ActivityDetailsGetResponseDTO;
import com.tw.clubmanagement.controller.representation.JoinedActivityGetResponseDTO;
import com.tw.clubmanagement.controller.representation.ReleaseActivityPostRequestDTO;
import com.tw.clubmanagement.controller.representation.VisibleActivityGetResponseDTO;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity")
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
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        releaseActivityPostRequestDTO.check();

        Activity activity = releaseActivityPostRequestDTO.toActivity();
        return activityService.releaseActivity(currentUserId, activity);
    }

    @DeleteMapping("/{activityId}")
    public void deleteActivityById(@RequestHeader("currentUserId") Integer currentUserId,
                                   @PathVariable String activityId) {
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
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }

        return activityFacadeService.getJoinedActivities(currentUserId);
    }

    @GetMapping("/list/visible")
    public List<VisibleActivityGetResponseDTO> getVisibleActivities(
            @RequestHeader("currentUserId") Integer currentUserId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }

        return activityFacadeService.getVisibleActivities(currentUserId);
    }

    @GetMapping("/{activityId}")
    public ActivityDetailsGetResponseDTO getActivityDetailsById(@RequestHeader("currentUserId") Integer currentUserId,
                                                                @PathVariable String activityId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(activityId).matches()) {
            throw new ValidationException("活动ID必须是一个正整数");
        }

        return activityFacadeService.getActivityDetailsById(currentUserId, Integer.valueOf(activityId));
    }

    @GetMapping("/club/{clubId}")
    public List<VisibleActivityGetResponseDTO> getVisibleActivitiesByClubId(
            @RequestHeader("currentUserId") Integer currentUserId, @PathVariable String clubId) {
        if (currentUserId == null) {
            throw new ValidationException("未指定当前用户ID");
        }
        if (!POSITIVE_PATTERN.matcher(clubId).matches()) {
            throw new ValidationException("俱乐部ID必须是一个正整数");
        }

        return activityFacadeService.getVisibleActivities(currentUserId).stream()
                .filter(visibleActivity -> visibleActivity.getClubId().equals((Integer.valueOf(clubId))))
                .collect(Collectors.toList());
    }

    @PutMapping("/{activityId}/join")
    public void joinActivity(@RequestHeader("currentUserId") Integer currentUserId, @PathVariable String activityId) {
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
