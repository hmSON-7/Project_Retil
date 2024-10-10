package com.project.Retil.group.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 그룹장이 그룹의 소개 글을 수정하기 위해 입력하는 정보를 받는 DTO
 * 순서대로 요청한 사용자 번호, 수정하려는 신규 소개 글
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGroupDTO {

    @NotEmpty
    private Long user_id;

    @NotEmpty
    private String introduce;
}