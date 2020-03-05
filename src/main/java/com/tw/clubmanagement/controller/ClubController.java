package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.ClubRepresentation;
import com.tw.clubmanagement.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClubController {
    private ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("club")
    public List<ClubRepresentation>getClubs() {
        return clubService.getAllClubs();
    }
}
