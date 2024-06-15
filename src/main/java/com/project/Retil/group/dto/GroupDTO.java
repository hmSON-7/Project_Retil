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
    private Long group_id;

    @NotEmpty
    private String groupName;

    @NotEmpty
    private String introduce;

    @NotEmpty
    private String ownerName;

    @NotEmpty
    private int limit;

    @NotEmpty
    private int current;

}