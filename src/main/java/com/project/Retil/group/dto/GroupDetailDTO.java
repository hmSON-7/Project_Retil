package com.project.Retil.group.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailDTO {

    @NotEmpty
    private Long groupId;

    @NotEmpty
    private String groupName;

    @NotEmpty
    private String groupIntroduce;

    @NotEmpty
    private String groupOwner;

    @NotEmpty
    private int memberCurrent;

    @NotEmpty
    private int memberLimit;

    private List<GroupMemberDTO> memberList;
}