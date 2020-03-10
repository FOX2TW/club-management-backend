package com.tw.clubmanagement.service;

import com.tw.clubmanagement.entity.UserEntity;
import com.tw.clubmanagement.exception.ResourceNotFoundException;
import com.tw.clubmanagement.model.UserInformation;
import com.tw.clubmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInformation getUserInformation(Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        UserEntity userEntity = userEntityOptional.orElseThrow(() -> new ResourceNotFoundException("未发现用户信息"));
        return userEntity.toUserInformation();
    }

    public List<UserInformation> getUserInformations(List<Integer> userIds) {
        return userRepository.findAllById(userIds).stream()
                .map(UserEntity::toUserInformation).collect(Collectors.toList());
    }
}
