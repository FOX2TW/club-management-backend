package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ApplicationRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecordEntity, Integer> {
    List<ApplicationRecordEntity> findByUserIdAndStatus(Integer userId, Integer status);

    Optional<ApplicationRecordEntity> findByUserIdAndClubId(Integer userId, Integer clubIb);

    List<ApplicationRecordEntity> findByStatus(Integer status);

    List<ApplicationRecordEntity> findByStatusAndClubIdIn(Integer status, List<Integer> clubIds);
}
