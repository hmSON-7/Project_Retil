package com.project.Retil.group.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 그룹을 생성하기 위해 입력한 값들을 받아주는 DTO
 * 순서대로 그룹명, 소개글, 제한 인원
 */
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