package com.tw.clubmanagement.service;

import com.tw.clubmanagement.entity.ActivityParticipantEntity;
import com.tw.clubmanagement.model.ActivityParticipant;
import com.tw.clubmanagement.repository.ActivityParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityParticipantService {
    private ActivityParticipantRepository activityParticipantRepository;

    @Autowired
    public ActivityParticipantService(ActivityParticipantRepository activityParticipantRepository) {
        this.activityParticipantRepository = activityParticipantRepository;
    }

    public List<Integer> getInvolvedActivityIds(Integer userId) {
        return getInvolvedActivities(userId).stream()
                .map(ActivityParticipant::getActivityId).collect(Collectors.toList());
    }

    public List<ActivityParticipant> getInvolvedActivities(Integer userId) {
        return activityParticipantRepository.findAllByParticipantId(userId).stream()
                .map(ActivityParticipantEntity::toActivityParticipant).collect(Collectors.toList());
    }

    public void save(ActivityParticipant activityParticipant) {
        activityParticipantRepository.save(ActivityParticipantEntity.fromActivityParticipant(activityParticipant));
    }

    public List<Integer> getParticipantIds(Integer activityId) {
        return activityParticipantRepository.findAllByActivityId(activityId).stream()
                .map(ActivityParticipantEntity::toActivityParticipant)
                .map(ActivityParticipant::getParticipantId)
                .collect(Collectors.toList());
    }
}
