package com.tw.clubmanagement.application;

import com.tw.clubmanagement.controller.representation.ActivityDetailsGetResponseDTO;
import com.tw.clubmanagement.controller.representation.JoinedActivityGetResponseDTO;
import com.tw.clubmanagement.controller.representation.VisibleActivityGetResponseDTO;
import com.tw.clubmanagement.model.Activity;
import com.tw.clubmanagement.service.ActivityParticipantService;
import com.tw.clubmanagement.service.ActivityService;
import com.tw.clubmanagement.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ActivityFacadeService {
    private ActivityService activityService;
    private ActivityParticipantService activityParticipantService;
    private ClubService clubService;

    @Autowired
    public ActivityFacadeService(ActivityService activityService,
                                 ActivityParticipantService activityParticipantService,
                                 ClubService clubService) {
        this.activityService = activityService;
        this.activityParticipantService = activityParticipantService;
        this.clubService = clubService;
    }

    @Transactional
    public List<JoinedActivityGetResponseDTO> getJoinedActivities(Integer userId) {
        List<Integer> managedClubIds = activityService.getManagedClubIds(userId);
        Map<Integer, String> clubIdNameMap = clubService.getIdNameMap();

        List<Activity> joinedActivities = activityService.getJoinedActivities(userId);

        return JoinedActivityGetResponseDTO.fromActivityWithRole(joinedActivities, managedClubIds, clubIdNameMap);
    }

    @Transactional
    public List<VisibleActivityGetResponseDTO> getVisibleActivities(Integer userId) {
        List<Integer> managedClubIds = activityService.getManagedClubIds(userId);
        Map<Integer, String> clubIdNameMap = clubService.getIdNameMap();

        List<Activity> joinedActivities = activityService.getJoinedActivities(userId);
        List<Activity> memberVisibleActivities = activityService.getMemberVisibleActivities(userId);
        List<Activity> openActivities = activityService.getOpenActivities();

        return VisibleActivityGetResponseDTO.fromActivitiesWithRole(
                joinedActivities, memberVisibleActivities, openActivities, managedClubIds, clubIdNameMap);
    }

    public ActivityDetailsGetResponseDTO getActivityDetailsById(Integer userId, Integer activityId) {
        Activity activity = activityService.getActivityById(activityId);
        List<Integer> participantIds = activityService.getParticipantIds(activityId);
        List<Integer> managedClubIds = activityService.getManagedClubIds(userId);
        List<Integer> joinedActivityIds = activityParticipantService.getJoinedActivityIds(userId);
        List<Integer> clubIds = clubService.getClubIds(userId);
        Map<Integer, String> clubIdNameMap = clubService.getIdNameMap();

        return ActivityDetailsGetResponseDTO.from(
                activity, participantIds, managedClubIds, joinedActivityIds, clubIds, clubIdNameMap);
    }
}
