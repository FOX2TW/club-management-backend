package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.ClubMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMemberEntity, Integer> {
    List<ClubMemberEntity> findAllByUserId(Integer userId);
}
