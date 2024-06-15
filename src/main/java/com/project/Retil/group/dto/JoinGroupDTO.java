package com.project.Retil.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 특정 그룹에 가입하기 위해 필요한 정보를 받아오는 DTO
 * 순서대로 가입하려는 사용자의 번호, 대상 그룹 이름
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDTO {

    @NotEmpty
    private Long user_id;

    @NotEmpty
    @NotBlank
    private String groupName;
}