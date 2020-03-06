package com.tw.clubmanagement.service;

import com.github.dozermapper.core.Mapper;
import com.tw.clubmanagement.controller.representation.*;
import com.tw.clubmanagement.entity.ClubEntity;
import com.tw.clubmanagement.entity.ClubMemberEntity;
import com.tw.clubmanagement.enums.ClubType;
import com.tw.clubmanagement.exception.ResourceNotFoundException;
import com.tw.clubmanagement.model.ClubInformation;
import com.tw.clubmanagement.model.ClubMember;
import com.tw.clubmanagement.repository.ClubMemberRepository;
import com.tw.clubmanagement.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final Mapper beanMapper;

    @Autowired
    public ClubService(ClubRepository clubRepository, ClubMemberRepository clubMemberRepository, Mapper beanMapper) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.beanMapper = beanMapper;
    }

    public List<ClubRepresentation> getAllClubs() {
        return clubRepository.findAll().stream()
                .sorted(Comparator.comparing(ClubEntity :: getCreatedAt).reversed())
                .map(clubEntity -> beanMapper.map(clubEntity, ClubRepresentation.class))
                .collect(Collectors.toList());
    }

    public List<ClubTypeRepresentation> getAllClubTypes() {
        ArrayList<ClubTypeRepresentation> clubTypeRepresentations = new ArrayList<>();
        for (ClubType clubType : ClubType.values()) {
            ClubTypeRepresentation clubTypeRepresentation = new ClubTypeRepresentation();
            clubTypeRepresentation.setId(clubType.getId());
            clubTypeRepresentation.setName(clubType.getName());
            clubTypeRepresentations.add(clubTypeRepresentation);
        }
        return clubTypeRepresentations;
    }

    public List<InvolvedClubGetResponseDTO> getInvolvedClub(Integer userId) {
        List<ClubMember> clubMembers = clubMemberRepository.findAllByUserId(userId)
                .stream().map(ClubMemberEntity::toClubMember).collect(Collectors.toList());

        List<Integer> clubIds = clubMembers.stream().map(ClubMember::getClubId).collect(Collectors.toList());

        List<ClubInformation> clubInformations = clubRepository.findAllById(clubIds)
                .stream().map(ClubEntity::toClubInformation).collect(Collectors.toList());

        return InvolvedClubGetResponseDTO.fromClubInformationAndClubMember(clubInformations, clubMembers);
    }

    public ClubDetailInfo getClubDetailInfo(Integer clubId) {
        ClubEntity clubEntity = clubRepository.findById(clubId).orElseThrow(() -> new ResourceNotFoundException("找不到指定俱乐部"));
        return ClubDetailInfo.builder()
                .introduction(clubEntity.getIntroduction())
                .name(clubEntity.getName())
                .picture(clubEntity.getPicture())
                .address(clubEntity.getAddress())
                .type(clubEntity.getType())
                .members(new ArrayList<>())
                .activities(new ArrayList<>())
                .build();
    }

    public void createClub(ClubCreateDTO clubCreateDTO) {
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setAddress(clubCreateDTO.getAddress());
        clubEntity.setName(clubCreateDTO.getName());
        clubEntity.setType(clubCreateDTO.getType());
        clubEntity.setIntroduction(clubCreateDTO.getIntroduction());
        clubEntity.setPicture(clubCreateDTO.getPicture());
        clubEntity.setCreatedBy(1L);
        clubRepository.save(clubEntity).toClubInformation();
    }
}
