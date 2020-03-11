package com.tw.clubmanagement.service;

import com.github.dozermapper.core.Mapper;
import com.tw.clubmanagement.controller.representation.*;
import com.tw.clubmanagement.entity.*;
import com.tw.clubmanagement.enums.ClubType;
import com.tw.clubmanagement.exception.ResourceNotFoundException;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.*;
import com.tw.clubmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private static final int AGREE = 1;
    private static final int REJECT = 2;
    private static final int UNPROCESSED = 0;

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplicationRecordRepository applicationRecordRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ActivityRepository activityRepository;
    private final Mapper beanMapper;

    @Autowired
    public ClubService(ClubRepository clubRepository, ClubMemberRepository clubMemberRepository,
                       ApplicationRecordRepository applicationRecordRepository, UserRepository userRepository,
                       UserService userService, ActivityRepository activityRepository, Mapper beanMapper) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.applicationRecordRepository = applicationRecordRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.activityRepository = activityRepository;
        this.beanMapper = beanMapper;
    }

    public Map<Integer, String> getIdNameMap() {
        return clubRepository.findAll().stream().collect(Collectors.toMap(ClubEntity::getId, ClubEntity::getName));
    }

    public List<ClubRepresentation> getAllClubs(Integer accessId) {
        List<ClubMember> clubMembers = clubMemberRepository.findAllByUserId(accessId)
                .stream().map(ClubMemberEntity::toClubMember).collect(Collectors.toList());

        List<Integer> allJoinedClubIds = clubMembers.stream().map(ClubMember::getClubId).collect(Collectors.toList());

        return clubRepository.findAll().stream()
                .filter(clubEntity -> clubEntity.isApproveStatus())
                .sorted(Comparator.comparing(ClubEntity::getCreatedAt).reversed())
                .map(clubEntity -> beanMapper.map(clubEntity, ClubRepresentation.class))
                .map(clubRepresentation -> clubRepresentation.isManager(accessId))
                .map(clubRepresentation -> clubRepresentation.isJoin(allJoinedClubIds))
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

    public List<Integer> getManagedClubIds(Integer userId) {
        return clubMemberRepository.findAllByUserId(userId).stream()
                .map(ClubMemberEntity::toClubMember)
                .filter(ClubMember::getIsManager)
                .map(ClubMember::getClubId).collect(Collectors.toList());
    }

    public List<Integer> getClubIds(Integer userId) {
        return clubMemberRepository.findAllByUserId(userId).stream()
                .map(ClubMemberEntity::toClubMember)
                .map(ClubMember::getClubId).collect(Collectors.toList());
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
        List<Integer> memberIds = clubMemberRepository.findByClubId(clubId).stream()
                .map(ClubMemberEntity::getUserId).collect(Collectors.toList());

        List<UserInformation> members = userRepository.findAllById(memberIds).stream()
                .map(UserEntity::toUserInformation).collect(Collectors.toList());

        List<Activity> activities = getActivitiesByClubId(clubId);

        return ClubDetailInfo.builder()
                .id(clubEntity.getId())
                .introduction(clubEntity.getIntroduction())
                .name(clubEntity.getName())
                .picture(clubEntity.getPicture())
                .address(clubEntity.getAddress())
                .type(clubEntity.getType())
                .members(members)
                .createdAt(clubEntity.getCreatedAt())
                .activities(activities)
                .build();
    }

    public void createClub(ClubCreateDTO clubCreateDTO, Integer userId) {
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setAddress(clubCreateDTO.getAddress());
        clubEntity.setName(clubCreateDTO.getName());
        clubEntity.setType(clubCreateDTO.getType());
        clubEntity.setIntroduction(clubCreateDTO.getIntroduction());
        clubEntity.setPicture(clubCreateDTO.getPicture());
        clubEntity.setCreatedBy(userId);
        clubRepository.save(clubEntity).toClubInformation();
    }

    public void updateClub(ClubUpdateDTO clubUpdateDTO, Integer userId) {
        Integer id = clubUpdateDTO.getId();
        ClubEntity clubEntity = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("找不到指定俱乐部"));

        clubEntity.setType(clubUpdateDTO.getType());
        clubEntity.setPicture(clubUpdateDTO.getPicture());
        clubEntity.setName(clubUpdateDTO.getName());
        clubEntity.setIntroduction(clubUpdateDTO.getIntroduction());
        clubEntity.setAddress(clubUpdateDTO.getAddress());
        clubEntity.setUpdatedBy(userId);
        clubRepository.save(clubEntity);
    }

    public void createApplicationRecord(ApplcationRecordCreateDTO applcationRecordCreateDTO, Integer applicantId) {
        Optional<ApplicationRecordEntity> applicationRecordEntityOptional = applicationRecordRepository.
                findByUserIdAndClubId(applicantId, applcationRecordCreateDTO.getClubId());
        if (applicationRecordEntityOptional.isPresent()
                && applicationRecordEntityOptional.get().getStatus() == ApplicationRecordEntity.UNPROCESSED) {
            throw new ValidationException("你已经申请过加入该俱乐部，请等待管理员审核");
        }

        ApplicationRecordEntity recordEntity = applcationRecordCreateDTO.toApplicationRecordEntity();
        recordEntity.setUserId(applicantId);
        applicationRecordRepository.save(recordEntity);
    }

    @Transactional
    public void processApplication(ApplcationProcessDTO processDTO) {
        ApplicationRecordEntity recordEntity = applicationRecordRepository.findById(processDTO.getRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("找不到指定申请记录"));
        if (recordEntity.getStatus() != UNPROCESSED) {
            throw new ValidationException("该申请已处理过了");
        }

        recordEntity.setStatus(processDTO.isAgree() ? AGREE : REJECT);
        recordEntity.setManagerComment(processDTO.getManagerComment());
        applicationRecordRepository.save(recordEntity);

        ClubMemberEntity clubMemberEntity = new ClubMemberEntity();
        clubMemberEntity.setClubId(recordEntity.getClubId());
        clubMemberEntity.setUserId(recordEntity.getUserId());
        clubMemberEntity.setManagerFlag(false);
        clubMemberRepository.save(clubMemberEntity);
    }

    public void deleteClubMember(Integer clubIb, Integer userId, Integer accessId) throws AccessDeniedException {
        clubMemberRepository.findByClubIdAndUserIdAndManagerFlag(clubIb, accessId, true)
                .orElseThrow(() -> new AccessDeniedException("仅俱乐部管理员有删除成员权限"));

        ClubMemberEntity clubMemberEntity = clubMemberRepository.findByClubIdAndUserId(clubIb, userId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到对应记录"));

        clubMemberRepository.deleteById(clubMemberEntity.getId());
    }

    public void quitClub(Integer clubIb, Integer userId) {
        ClubMemberEntity clubMemberEntity = clubMemberRepository.findByClubIdAndUserId(clubIb, userId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到对应记录"));

        clubMemberRepository.deleteById(clubMemberEntity.getId());
    }

    @Transactional
    public void processClub(ClubProcessDTO clubProcessDTO, Integer accessId) {
        ClubEntity clubEntity = clubRepository.findById(clubProcessDTO.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("俱乐部不存在"));
        if (clubEntity.isProcessStatus()) {
            throw new ValidationException("该俱乐部创建申请已处理");
        }

        clubEntity.setUpdatedBy(accessId);
        clubEntity.setProcessStatus(true);
        clubEntity.setApproveStatus(clubProcessDTO.isApproveStatus());
        clubRepository.save(clubEntity);

        //将创建者自动加入成员，并设置成manager
        ClubMemberEntity clubMemberEntity = new ClubMemberEntity();
        clubMemberEntity.setClubId(clubEntity.getId());
        clubMemberEntity.setUserId((int) clubEntity.getCreatedBy());
        clubMemberEntity.setManagerFlag(true);
        clubMemberRepository.save(clubMemberEntity);
    }

    public List<ClubApplication> getClubApplication(Integer creatorId) {
        UserInformation userInformation = userService.getUserInformation(creatorId);
        List<ClubApplication> clubApplications = clubRepository.findByCreatedByAndProcessStatus(creatorId, false).stream()
                .map(ClubEntity::toClubApplication).collect(Collectors.toList());

        clubApplications.forEach(clubApplication -> clubApplication.setCreatorName(userInformation.getUsername()));
        return clubApplications;
    }

    public List<JoinApplicationDTO> getJoinApplications(Integer currentUserId) {
        List<ApplicationRecordEntity> applicationRecordEntities =
                applicationRecordRepository.findByUserIdAndStatus(currentUserId, ApplicationRecordEntity.UNPROCESSED);
        List<Integer> clubIds = applicationRecordEntities.stream()
                .map(ApplicationRecordEntity::getClubId).collect(Collectors.toList());
        Map<Integer, ClubEntity> clubEntityMap = clubRepository.findAllById(clubIds).stream()
                .collect(Collectors.toMap(c -> c.getId(), c -> c));

        List<JoinApplicationDTO> applicationDTOS = applicationRecordEntities.stream()
                .map(ApplicationRecordEntity::toJoinApplicationDTO).collect(Collectors.toList());

        UserInformation userInformation = userService.getUserInformation(currentUserId);
        applicationDTOS.forEach(joinApplicationDTO -> {
            joinApplicationDTO.setClubName(clubEntityMap.get(joinApplicationDTO.getClubId()).getName());
            joinApplicationDTO.setApplicantName(userInformation.getUsername());
        });

        return applicationDTOS;
    }

    public List<ClubApplication> getCreateApplicationsByAdmin() {
        List<ClubEntity> clubEntities = clubRepository.findByProcessStatus(false);
        List<ClubApplication> clubApplications = clubEntities.stream()
                .map(ClubEntity::toClubApplication).collect(Collectors.toList());
        List<Integer> creatorIds = clubEntities.stream()
                .map(clubEntity -> clubEntity.getCreatedBy()).distinct().collect(Collectors.toList());
        Map<Integer, UserEntity> userEntityMap = userRepository.findAllById(creatorIds)
                .stream().collect(Collectors.toMap(u -> u.getId(), u -> u));
        clubApplications.forEach(clubApplication ->
                clubApplication.setCreatorName(userEntityMap.get(clubApplication.getCreatorId()).getUsername()));

        return clubApplications;
    }

    public void cancelJoinApplication(Integer clubIb, Integer currentUserId) {
        ApplicationRecordEntity recordEntity = applicationRecordRepository.findByUserIdAndClubId(currentUserId, clubIb)
                .orElseThrow(() -> new ResourceNotFoundException("无此申请记录"));
        applicationRecordRepository.deleteById(recordEntity.getId());

    }

    private List<Activity> getActivitiesByClubId(Integer clubId) {
        return activityRepository.findAllByClubId(clubId).stream()
                .filter(activityEntity -> activityEntity.getEndTime().after(new Date()))
                .sorted(Comparator.comparing(ActivityEntity::getStartTime))
                .map(ActivityEntity::toActivity).collect(Collectors.toList());
    }

    public List<JoinApplicationDTO> getJoinApplicationsByManager(Integer managerId) {

        List<ClubMemberEntity> clubMemberEntities = clubMemberRepository.findByUserIdAndManagerFlag(managerId, true);
        List<Integer> allManagedClubIds = clubMemberEntities.stream()
                .map(ClubMemberEntity::getClubId)
                .collect(Collectors.toList());
        List<ApplicationRecordEntity> recordEntities = applicationRecordRepository
                .findByStatusAndClubIdIn(ApplicationRecordEntity.UNPROCESSED, allManagedClubIds);

        List<Integer> clubIds = recordEntities.stream()
                .map(ApplicationRecordEntity::getClubId).collect(Collectors.toList());
        Map<Integer, ClubEntity> clubEntityMap = clubRepository.findAllById(clubIds).stream()
                .collect(Collectors.toMap(c -> c.getId(), c -> c));

        List<Integer> userIds = recordEntities.stream()
                .map(ApplicationRecordEntity::getUserId).collect(Collectors.toList());
        Map<Integer, UserEntity> userEntityMap = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(u -> u.getId(), u -> u));

        List<JoinApplicationDTO> applicationDTOS = recordEntities.stream()
                .map(ApplicationRecordEntity::toJoinApplicationDTO).collect(Collectors.toList());
        applicationDTOS.forEach(joinApplicationDTO -> {
            joinApplicationDTO.setApplicantName(userEntityMap.get(joinApplicationDTO.getApplicantId()).getUsername());
            joinApplicationDTO.setClubName(clubEntityMap.get(joinApplicationDTO.getClubId()).getName());
        });

        return applicationDTOS;
    }

    public void deleteClub(Integer clubId, Integer currentUserId) throws AccessDeniedException {
        ClubEntity clubEntity = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("俱乐部不存在"));
        if (clubEntity.getCreatedBy() != currentUserId) {
            throw new AccessDeniedException("仅能删除自己创建的俱乐部");
        }

        clubRepository.deleteById(clubId);
    }

    public boolean isClubMember(Integer clubId, Integer userId) {
        return clubMemberRepository.findByClubIdAndUserId(clubId, userId).isPresent();
    }
}
