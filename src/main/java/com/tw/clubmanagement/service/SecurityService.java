package com.tw.clubmanagement.service;

import com.tw.clubmanagement.entity.Role;
import com.tw.clubmanagement.entity.UserRole;
import com.tw.clubmanagement.exception.ResourceNotFoundException;
import com.tw.clubmanagement.repository.RoleRepository;
import com.tw.clubmanagement.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public SecurityService(RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("没有叫该名字的Role"));
    }

    public UserRole findByUserIdAndRoleId(Integer userId, Integer roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId);
    }
}
