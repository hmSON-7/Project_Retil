package com.project.Retil.group.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberDTO {

    @NotEmpty
    private Long userId;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private Long todayStudyTime;

    @NotEmpty
    private String userRank; // New field
}