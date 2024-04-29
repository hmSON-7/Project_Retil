package com.project.retil.user.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 세션에 멤버 값을 저장하기 위해 사용하는 DTO 입니다.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDTO {
    /**
     * 유저를 식별하기 위한 기본키
     */
    private String email;
}