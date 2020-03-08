package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.*;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public List<ClubRepresentation> getClubs() {
        return clubService.getAllClubs();
    }

    @PostMapping
    public void createClub(@RequestBody @Valid ClubCreateDTO clubCreateDTO) {
        clubService.createClub(clubCreateDTO);
    }

    @PutMapping
    public void updateClub(@RequestBody @Valid ClubUpdateDTO clubUpdateDTO) {
        clubService.updateClub(clubUpdateDTO);
    }

    @GetMapping("{clubId}")
    public ClubDetailInfo getClubDetailInfo(@PathVariable @Min(value = 1, message = CLUB_ID_INVALID_MESSAGE) Integer clubId) {
        return clubService.getClubDetailInfo(clubId);
    }

    @GetMapping("type")
    public List<ClubTypeRepresentation> getClubTypes() {
        return clubService.getAllClubTypes();
    }

    @GetMapping("/user/{userId}")
    public List<InvolvedClubGetResponseDTO> getInvolvedClubs(@PathVariable String userId) {
        if (!POSITIVE_PATTERN.matcher(userId).matches()) {
            throw new ValidationException(USERID_INVALID_MESSAGE);
        }

        return clubService.getInvolvedClub(Integer.valueOf(userId));
    }
}
