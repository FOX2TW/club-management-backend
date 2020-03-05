package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.ClubRepresentation;
import com.tw.clubmanagement.controller.representation.ClubTypeRepresentation;
import com.tw.clubmanagement.controller.representation.CommonResponse;
import com.tw.clubmanagement.controller.representation.CommonResult;
import com.tw.clubmanagement.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "club")
public class ClubController {
    private ClubService clubService;

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
}
