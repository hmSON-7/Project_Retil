package com.project.Retil.userAccount.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDTO {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String email;

    @NotEmpty
    private LocalDateTime latestPwChange;
}
