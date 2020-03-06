package com.tw.clubmanagement.controller.representation;

import com.tw.clubmanagement.model.ClubInformation;
import com.tw.clubmanagement.model.ClubMember;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class InvolvedClubGetResponseDTO {
    private Integer id;
    private String name;
    private Integer type;
    private String picture;
    private Boolean approveStatus;
    private Boolean isManager;
    private String introduction;

    public static List<InvolvedClubGetResponseDTO> fromClubInformationAndClubMember(
            List<ClubInformation> clubInformations, List<ClubMember> clubMembers) {

        List<InvolvedClubGetResponseDTO> involvedClubGetResponseDTOList = new ArrayList<>();
        for (ClubInformation clubInformation : clubInformations) {
            Boolean isManager = clubMembers.stream()
                    .filter(clubMember -> clubMember.getClubId() == clubInformation.getId()).findFirst().orElse(null).getIsManager();

            involvedClubGetResponseDTOList.add(
                    InvolvedClubGetResponseDTO.builder()
                            .id(clubInformation.getId())
                            .name(clubInformation.getName())
                            .type(clubInformation.getType())
                            .picture(clubInformation.getPicture())
                            .approveStatus(clubInformation.getApproveStatus())
                            .isManager(isManager)
                            .introduction(clubInformation.getIntroduction())
                            .build());
        }

        return involvedClubGetResponseDTOList;
    }
}
