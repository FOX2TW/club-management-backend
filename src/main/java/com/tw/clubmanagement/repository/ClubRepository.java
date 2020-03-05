package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Integer> {
}
