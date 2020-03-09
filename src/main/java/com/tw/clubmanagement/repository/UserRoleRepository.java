package com.tw.clubmanagement.repository;

import com.tw.clubmanagement.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
  List<UserRole> findByUserId(Integer userId);

  UserRole findByUserIdAndRoleId(Integer userId, Integer roleId);
}
