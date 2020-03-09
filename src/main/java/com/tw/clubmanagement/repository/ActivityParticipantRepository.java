package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ActivityParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityParticipantRepository extends JpaRepository<ActivityParticipantEntity, Integer> {
    List<ActivityParticipantEntity> findAllByParticipantId(Integer participantId);
    List<ActivityParticipantEntity> findAllByActivityId(Integer activityId);
}
