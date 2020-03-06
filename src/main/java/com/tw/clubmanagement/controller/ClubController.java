package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.*;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "club")
public class ClubController {
    private static final Pattern POSITIVE_PATTERN = Pattern.compile("^[1-9]?[0-9]*$");
    private static final String USERID_INVALID_MESSAGE = "用户ID必须是一个正整数";

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public CommonResult<List<ClubRepresentation>> getClubs() {
        return CommonResponse.success(clubService.getAllClubs());
    }

    @GetMapping("type")
    public CommonResult<List<ClubTypeRepresentation>> getClubTypes() {
        return CommonResponse.success(clubService.getAllClubTypes());
    }

    @GetMapping("/user/{userId}")
    public List<InvolvedClubGetResponseDTO> getInvolvedClubs(@PathVariable String userId) {
        if (!POSITIVE_PATTERN.matcher(userId).matches()) {
            throw new ValidationException(USERID_INVALID_MESSAGE);
        }

        return clubService.getInvolvedClub(Integer.valueOf(userId));
    }
}
