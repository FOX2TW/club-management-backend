package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ApplicationRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecordEntity, Integer> {
}
