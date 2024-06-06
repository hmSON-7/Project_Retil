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
public class CreateGroupDTO {

    @NotEmpty
    private String groupName;

    @NotEmpty
    private String introduce;

    @NotEmpty
    private int limit;
}