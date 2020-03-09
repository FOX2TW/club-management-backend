package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {
    List<ActivityEntity> findAllByClubId(Integer clubId);

    List<ActivityEntity> findAllByClubId(Iterable<Integer> clubIds);

    List<ActivityEntity> findAllByOpen(Integer open);
}
