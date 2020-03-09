package com.tw.clubmanagement.controller.representation;

import com.tw.clubmanagement.model.ClubApplication;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubApplicationGetDTO {
    private String creatorName;
    private List<ClubApplication> applications;
}
