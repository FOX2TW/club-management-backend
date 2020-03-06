package com.tw.clubmanagement.service;

import com.tw.clubmanagement.entity.ActivityEntity;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private ActivityRepository activityRepository;
    private ActivityParticipantService activityParticipantService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository,
                           ActivityParticipantService activityParticipantService) {
        this.activityRepository = activityRepository;
        this.activityParticipantService = activityParticipantService;
    }

    public List<Activity> getInvolvedActivities(Integer userId) {
        List<Integer> involvedActivityIds = activityParticipantService.getInvolvedActivityIds(userId);
        return activityRepository.findAllById(involvedActivityIds).stream()
                .map(ActivityEntity::toActivity).collect(Collectors.toList());
    }
}
