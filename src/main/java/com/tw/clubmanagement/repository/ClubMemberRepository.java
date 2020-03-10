package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ClubMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMemberEntity, Integer> {
    List<ClubMemberEntity> findAllByUserId(Integer userId);

    List<ClubMemberEntity> findByClubId(Integer clubId);

    Optional<ClubMemberEntity> findByClubIdAndUserId(Integer clubIb, Integer userId);

    Optional<ClubMemberEntity> findByClubIdAndUserIdAndManagerFlag(Integer clubIb, Integer userId, boolean managerFlag);

    List<ClubMemberEntity> findByUserIdAndManagerFlag(Integer managerId, boolean managerFlag);
}
