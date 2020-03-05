package com.tw.clubmanagement.service;

import com.github.dozermapper.core.Mapper;
import com.tw.clubmanagement.controller.representation.ClubRepresentation;
import com.tw.clubmanagement.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private ClubRepository clubRepository;
    private final Mapper beanMapper;

    @Autowired
    public ClubService(ClubRepository clubRepository, Mapper beanMapper) {
        this.clubRepository = clubRepository;
        this.beanMapper = beanMapper;
    }

    public List<ClubRepresentation> getAllClubs() {
        return clubRepository.findAll().stream()
                .map(clubEntity -> beanMapper.map(clubEntity, ClubRepresentation.class))
                .collect(Collectors.toList());
    }
}
