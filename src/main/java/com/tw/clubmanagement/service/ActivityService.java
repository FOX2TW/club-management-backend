package com.tw.clubmanagement.service;

import com.tw.clubmanagement.entity.ActivityEntity;
import com.tw.clubmanagement.exception.InternalServerErrorException;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.model.ActivityParticipant;
import com.tw.clubmanagement.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private ActivityRepository activityRepository;
    private ActivityParticipantService activityParticipantService;
    private ClubService clubService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository,
                           ActivityParticipantService activityParticipantService,
                           ClubService clubService) {
        this.activityRepository = activityRepository;
        this.activityParticipantService = activityParticipantService;
        this.clubService = clubService;
    }

    @Transactional(readOnly = true)
    public List<Activity> getInvolvedActivities(Integer userId) {
        List<Integer> involvedActivityIds = activityParticipantService.getInvolvedActivityIds(userId);

        return activityRepository.findAllById(involvedActivityIds).stream()
                .map(ActivityEntity::toActivity).collect(Collectors.toList());
    }

    public List<ActivityParticipant> getInvolvedActivityParticipants(Integer userId) {
        return activityParticipantService.getInvolvedActivities(userId);
    }

    @Transactional
    public Integer releaseActivity(Integer currentUserId, Activity activity) {
        List<Integer> managedClubIds = clubService.getManagedClubIds(currentUserId);
        if (managedClubIds.contains(activity.getClubId())) {
            throw new ValidationException("当前用户无权在指定俱乐部下创建活动");
        }

        try {
            ActivityEntity activityEntity = ActivityEntity.fromActivity(activity);
            Activity savedActivity = activityRepository.save(activityEntity).toActivity();

            ActivityParticipant activityParticipant = ActivityParticipant.builder()
                    .activityId(savedActivity.getId())
                    .participantId(currentUserId)
                    .role(2)
                    .build();
            activityParticipantService.save(activityParticipant);

            return savedActivity.getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("保存数据失败");
        }
    }

    public List<Activity> getActivities() {
        return activityRepository.findAll().stream().map(ActivityEntity::toActivity).collect(Collectors.toList());
    }

    public Activity getActivityById(Integer activityId) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId).map(ActivityEntity::toActivity);
        if (!activityOptional.isPresent()) {
            throw new InternalServerErrorException("未找到活动详细信息");
        }

        return activityOptional.get();
    }

    public List<Integer> getParticipantIds(Integer activityId) {
        return activityParticipantService.getParticipantIds(activityId);
    }

    public void deleteActivityById(Integer activityId) {
        activityRepository.deleteById(activityId);
    }

    public List<Activity> getActivitiesByClubId(Integer clubId) {
        return activityRepository.findAllByClubId(clubId).stream().map(ActivityEntity::toActivity).collect(Collectors.toList());
    }
}
