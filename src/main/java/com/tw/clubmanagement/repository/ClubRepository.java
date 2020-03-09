package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Integer> {
    List<ClubEntity> findByCreatedByAndProcessStatus(Long createdBy, boolean processStatus);
}
