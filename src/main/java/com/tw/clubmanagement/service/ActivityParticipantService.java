package com.tw.clubmanagement.service;

import com.tw.clubmanagement.entity.ActivityParticipantEntity;
import com.tw.clubmanagement.model.ActivityParticipant;
import com.tw.clubmanagement.repository.ActivityParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityParticipantService {
    private ActivityParticipantRepository activityParticipantRepository;

    @Autowired
    public ActivityParticipantService(ActivityParticipantRepository activityParticipantRepository) {
        this.activityParticipantRepository = activityParticipantRepository;
    }

    public List<Integer> getJoinedActivityIds(Integer userId) {
        return getJoinedActivities(userId).stream()
                .map(ActivityParticipant::getActivityId).collect(Collectors.toList());
    }

    public List<ActivityParticipant> getJoinedActivities(Integer userId) {
        return activityParticipantRepository.findAllByParticipantId(userId).stream()
                .map(ActivityParticipantEntity::toActivityParticipant).collect(Collectors.toList());
    }

    public void join(ActivityParticipant activityParticipant) {
        ActivityParticipantEntity activityParticipantEntity =
                ActivityParticipantEntity.fromActivityParticipant(activityParticipant);
        activityParticipantEntity.setCreatedBy(activityParticipant.getParticipantId());
        activityParticipantEntity.setUpdatedBy(activityParticipant.getParticipantId());
        activityParticipantRepository.save(activityParticipantEntity);
    }

    public List<Integer> getParticipantIds(Integer activityId) {
        return activityParticipantRepository.findAllByActivityId(activityId).stream()
                .map(ActivityParticipantEntity::toActivityParticipant)
                .map(ActivityParticipant::getParticipantId)
                .collect(Collectors.toList());
    }

    public void deleteByActivityId(Integer activityId) {
        activityParticipantRepository.deleteByActivityId(activityId);
    }

    public Optional<ActivityParticipant> findByActivityIdAndParticipantId(Integer activityId, Integer userId) {
        return activityParticipantRepository
                .findByActivityIdAndParticipantId(activityId, userId)
                .map(ActivityParticipantEntity::toActivityParticipant);
    }

    public void deleteActivityParticipant(Integer activityId, Integer userId) {
        activityParticipantRepository.deleteByActivityIdAndParticipantId(activityId, userId);
    }
}
