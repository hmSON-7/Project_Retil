package com.project.Retil.group.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String groupName;

    @NotEmpty
    private String groupOwner;

    @NotEmpty
    private String groupIntroduce;

    @NotEmpty
    private int memberLimit;

    @NotEmpty
    private int memberCurrent;

}