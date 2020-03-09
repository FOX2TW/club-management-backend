package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ActivityParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityParticipantRepository extends JpaRepository<ActivityParticipantEntity, Integer> {
    List<ActivityParticipantEntity> findAllByParticipantId(Integer participantId);

    List<ActivityParticipantEntity> findAllByActivityId(Integer activityId);

    void deleteByActivityId(Integer activityId);

    Optional<ActivityParticipantEntity> findByActivityIdAndParticipantId(Integer activityId, Integer participantId);

    @Transactional
    void deleteByActivityIdAndParticipantId(Integer activityId, Integer participantId);
}
