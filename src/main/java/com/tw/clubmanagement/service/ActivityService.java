package com.tw.clubmanagement.service;

import com.tw.clubmanagement.entity.ActivityEntity;
import com.tw.clubmanagement.exception.InternalServerErrorException;
import com.tw.clubmanagement.exception.ResourceNotFoundException;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.model.ActivityParticipant;
import com.tw.clubmanagement.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private ClubService clubService;
    private ActivityRepository activityRepository;
    private ActivityParticipantService activityParticipantService;

    @Autowired
    public ActivityService(ClubService clubService,
                           ActivityRepository activityRepository,
                           ActivityParticipantService activityParticipantService) {
        this.clubService = clubService;
        this.activityRepository = activityRepository;
        this.activityParticipantService = activityParticipantService;
    }

    @Transactional
    public Integer releaseActivity(Integer currentUserId, Activity activity) {
        List<Integer> managedClubIds = clubService.getManagedClubIds(currentUserId);
        if (!managedClubIds.contains(activity.getClubId())) {
            throw new ValidationException("当前用户无权在指定俱乐部下创建活动");
        }

        try {
            ActivityEntity activityEntity = ActivityEntity.fromActivity(activity);
            activityEntity.setCreatedBy(currentUserId);
            activityEntity.setUpdatedBy(currentUserId);
            Activity savedActivity = activityRepository.save(activityEntity).toActivity();

            ActivityParticipant activityParticipant = ActivityParticipant.builder()
                    .activityId(savedActivity.getId())
                    .participantId(currentUserId)
                    .role(2)
                    .build();
            activityParticipantService.join(activityParticipant);

            return savedActivity.getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("发布活动失败");
        }
    }

    @Transactional
    public void deleteActivityById(Integer currentUserId, Integer activityId) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId).map(ActivityEntity::toActivity);
        if (!activityOptional.isPresent()) {
            throw new ValidationException("待删除的活动不存在");
        }

        List<Integer> managedClubIds = clubService.getManagedClubIds(currentUserId);
        if (!managedClubIds.contains(activityOptional.get().getClubId())) {
            throw new ValidationException("当前用户非俱乐部管理员，无权删除活动");
        }

        try {
            activityParticipantService.deleteByActivityId(activityId);
            activityRepository.deleteById(activityId);
        } catch (Exception e) {
            throw new InternalServerErrorException("删除活动失败");
        }
    }

    public List<Activity> getJoinedActivities(Integer userId) {
        List<Integer> joinedActivityIds = activityParticipantService.getJoinedActivityIds(userId);

        return activityRepository.findAllById(joinedActivityIds).stream()
                .map(ActivityEntity::toActivity).collect(Collectors.toList());
    }

    public List<Integer> getManagedClubIds(Integer userId) {
        return clubService.getManagedClubIds(userId);
    }

    public List<Activity> getMemberVisibleActivities(Integer userId) {
        List<Integer> clubIds = clubService.getClubIds(userId);

        return activityRepository.findAllByClubIdIn(clubIds).stream()
                .map(ActivityEntity::toActivity).collect(Collectors.toList());
    }

    public List<Activity> getOpenActivities() {
        return activityRepository.findAllByOpen(1).stream()
                .map(ActivityEntity::toActivity).collect(Collectors.toList());
    }

    public Activity getActivityById(Integer activityId) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId).map(ActivityEntity::toActivity);
        if (!activityOptional.isPresent()) {
            throw new InternalServerErrorException("未找到活动详细信息");
        }

        return activityOptional.get();
    }

    @Transactional
    public void joinActivity(Integer userId, Integer activityId) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId).map(ActivityEntity::toActivity);
        if (!activityOptional.isPresent()) {
            throw new ValidationException("待报名的活动不存在");
        }

        List<Integer> memberClubIds = clubService.getClubIds(userId);
        if (!memberClubIds.contains(activityOptional.get().getClubId())) {
            throw new ValidationException("非俱乐部会员不能报名俱乐部的活动");
        }

        if (activityParticipantService.findByActivityIdAndParticipantId(activityId, userId).isPresent()) {
            throw new ValidationException("已经报名");
        }

        if (new Date().after(activityOptional.get().getJoinEndTime())) {
            throw new ValidationException("报名时间已截止");
        }

        if (activityOptional.get().getNumberLimitation() != 0 &&
                activityParticipantService.countByActivityId(activityId) >= activityOptional.get().getNumberLimitation()) {
            throw new ValidationException("活动报名已满");
        }

        ActivityParticipant activityParticipant = ActivityParticipant.builder()
                .activityId(activityId)
                .participantId(userId)
                .role(0)
                .build();

        updateNumberJoined(activityId, 1);

        activityParticipantService.join(activityParticipant);
    }

    @Transactional
    public void cancelJoiningActivity(Integer userId, Integer activityId) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId).map(ActivityEntity::toActivity);
        if (!activityOptional.isPresent()) {
            throw new ValidationException("待取消报名的活动不存在");
        }

        List<Integer> memberClubIds = clubService.getClubIds(userId);
        if (!memberClubIds.contains(activityOptional.get().getClubId())) {
            throw new ValidationException("非俱乐部会员不能取消报名俱乐部的活动");
        }

        if (!activityParticipantService.findByActivityIdAndParticipantId(activityId, userId).isPresent()) {
            throw new ValidationException("未报过名");
        }

        activityParticipantService.deleteActivityParticipant(activityId, userId);

        updateNumberJoined(activityId, -1);

    }

    private void updateNumberJoined(Integer activityId, int updateNumber) {
        ActivityEntity activityEntity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("活动不存在"));
        activityEntity.setNumberJoined(activityEntity.getNumberJoined()+updateNumber);
        activityRepository.save(activityEntity);
    }

    @Transactional
    public void thumbsUpActivity(Integer userId, Integer activityId) {
        // TODO
    }

    @Transactional
    public void cancelThumbsUpActivity(Integer userId, Integer activityId) {
        // TODO
    }

    public List<Integer> getParticipantIds(Integer activityId) {
        return activityParticipantService.getParticipantIds(activityId);
    }

    public void updateActivity(Integer currentUserId, Activity activity) {
        ActivityEntity activityEntity = activityRepository.findById(activity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("未找到对应活动"));

        activity.setClubId(activity.getId());
        activityEntity.setDescription(activity.getDescription());
        activityEntity.setName(activity.getName());
        activityEntity.setEndTime(activity.getEndTime());
        activityEntity.setJoinEndTime(activity.getJoinEndTime());
        activityEntity.setStartTime(activity.getStartTime());
        activityEntity.setOpen(activity.getOpen());
        activityEntity.setNumberLimitation(activity.getNumberLimitation());
        activityEntity.setThemePicture(activity.getThemePicture());
        activityEntity.setUpdatedBy(currentUserId);
        activityRepository.save(activityEntity);
    }
}
