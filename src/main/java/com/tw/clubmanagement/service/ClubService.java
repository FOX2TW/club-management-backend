package com.tw.clubmanagement.service;

import com.github.dozermapper.core.Mapper;
import com.tw.clubmanagement.controller.representation.ClubRepresentation;
import com.tw.clubmanagement.controller.representation.ClubTypeRepresentation;
import com.tw.clubmanagement.controller.representation.InvolvedClubGetResponseDTO;
import com.tw.clubmanagement.entity.ClubEntity;
import com.tw.clubmanagement.entity.ClubMemberEntity;
import com.tw.clubmanagement.enums.ClubType;
import com.tw.clubmanagement.model.ClubInformation;
import com.tw.clubmanagement.model.ClubMember;
import com.tw.clubmanagement.repository.ClubMemberRepository;
import com.tw.clubmanagement.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        List<ClubMember> clubMembers = clubMemberRepository.findAllByUserId()
                .stream().map(ClubMemberEntity::toClubMember).collect(Collectors.toList());

        List<Integer> clubIds = clubMembers.stream().map(ClubMember::getClubId).collect(Collectors.toList());

        List<ClubInformation> clubInformations = clubRepository.findAllById(clubIds)
                .stream().map(ClubEntity::toClubInformation).collect(Collectors.toList());

        return InvolvedClubGetResponseDTO.fromClubInformationAndClubMember(clubInformations, clubMembers);
    }
}
