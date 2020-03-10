package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.annotation.AccessPermission;
import com.tw.clubmanagement.controller.representation.*;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.model.ClubApplication;
import com.tw.clubmanagement.service.ClubService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "club")
@Validated
public class ClubController {
    private static final Pattern POSITIVE_PATTERN = Pattern.compile("^[1-9]?[0-9]*$");
    private static final String USERID_INVALID_MESSAGE = "用户ID必须是一个正整数";
    private static final String CLUB_ID_INVALID_MESSAGE = "俱乐部ID必须是一个正整数";

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    @ApiOperation(value = "获取所有俱乐部")
    public List<ClubRepresentation> getClubs(@RequestHeader Integer currentUserId) {
        return clubService.getAllClubs(currentUserId);
    }

    @PostMapping
    @ApiOperation(value = "创建俱乐部")
    public void createClub(@RequestBody @Valid ClubCreateDTO clubCreateDTO, @RequestHeader Integer currentUserId) {
        clubService.createClub(clubCreateDTO, currentUserId);
    }

    @PutMapping
    @ApiOperation(value = "更新俱乐部信息")
    public void updateClub(@RequestBody @Valid ClubUpdateDTO clubUpdateDTO, @RequestHeader Integer currentUserId) {
        clubService.updateClub(clubUpdateDTO, currentUserId);
    }

    @GetMapping("{clubId}")
    @ApiOperation(value = "查询俱乐部详情")
    public ClubDetailInfo getClubDetailInfo(@PathVariable @Min(value = 1, message = CLUB_ID_INVALID_MESSAGE) Integer clubId) {
        return clubService.getClubDetailInfo(clubId);
    }

    @GetMapping("type")
    @ApiOperation(value = "获取俱乐部类型")
    public List<ClubTypeRepresentation> getClubTypes() {
        return clubService.getAllClubTypes();
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "查询用户参与的俱乐部")
    public List<InvolvedClubGetResponseDTO> getInvolvedClubs(@PathVariable String userId) {
        if (!POSITIVE_PATTERN.matcher(userId).matches()) {
            throw new ValidationException(USERID_INVALID_MESSAGE);
        }

        return clubService.getInvolvedClub(Integer.valueOf(userId));
    }

    @PostMapping("join")
    @ApiOperation(value = "申请加入俱乐部")
    public void createApplicationRecord(@RequestBody @Valid ApplcationRecordCreateDTO applcationRecordCreateDTO) {
        clubService.createApplicationRecord(applcationRecordCreateDTO);
    }

    @PutMapping("club/member")
    @ApiOperation(value = "审批俱乐部成员")
    public void processApplication(@RequestBody @Valid ApplcationProcessDTO processDTO) {
        clubService.processApplication(processDTO);
    }

    @DeleteMapping("/{clubId}/member/{userId}")
    @ApiOperation(value = "删除俱乐部成员")
    public void deleteClubMember(@PathVariable(value = "clubId") @Min(value = 1, message = CLUB_ID_INVALID_MESSAGE)Integer clubId,
                                 @PathVariable(value = "userId") @Min(value = 1, message = USERID_INVALID_MESSAGE)Integer userId,
                                 @RequestHeader Integer currentUserId) throws AccessDeniedException {
        clubService.deleteClubMember(clubId, userId, currentUserId);
    }

    @DeleteMapping("/{clubIb}/member")
    @ApiOperation(value = "退出俱乐部")
    public void deleteClubMember(@PathVariable(value = "clubId") @Min(value = 1, message = CLUB_ID_INVALID_MESSAGE)Integer clubId,
                                 @RequestHeader Integer currentUserId) {
        clubService.quitClub(clubId, currentUserId);
    }

    @PutMapping("/{clubIb}/process")
    @AccessPermission(hasRole = "ROLE_ADMIN")
    @ApiOperation(value = "超级管理员处理俱乐部创建")
    public void processClub(@RequestBody @Valid  ClubProcessDTO clubProcessDTO,
                            @RequestHeader Integer currentUserId) {
        clubService.processClub(clubProcessDTO, currentUserId);
    }

    @GetMapping("/application")
    @ApiOperation(value = "查询用户创建俱乐部申请")
    public List<ClubApplication> getClubApplication(@RequestHeader Integer currentUserId) {
        return clubService.getClubApplication(currentUserId);
    }


    @GetMapping("/join/application")
    @ApiOperation(value = "查询用户加入俱乐部申请")
    public List<JoinApplicationDTO> getJoinApplications(@RequestHeader Integer currentUserId) {
        return clubService.getJoinApplications(currentUserId);
    }

    @DeleteMapping("/join/{clubId}")
    @ApiOperation(value = "用户取消加入俱乐部申请")
    public void cancelJoinApplication(@PathVariable @Min(value = 1, message = CLUB_ID_INVALID_MESSAGE)Integer clubId,
                                                          @RequestHeader Integer currentUserId) {
        clubService.cancelJoinApplication(clubId, currentUserId);
    }

    @GetMapping("/application/admin")
    @ApiOperation(value = "admin查询所有创建俱乐部申请")
    @AccessPermission(hasRole = "ROLE_ADMIN")
    public List<ClubApplication> getCreateApplicationsByAdmin(@RequestHeader Integer currentUserId) {
        return clubService.getCreateApplicationsByAdmin();
    }

    @GetMapping("/application/manager")
    @ApiOperation(value = "manager查询所有加入俱乐部申请")
    public List<JoinApplicationDTO> getJoinApplicationsByAdmin(@RequestHeader Integer currentUserId) {
        return clubService.getJoinApplicationsByManager(currentUserId);
    }

}
