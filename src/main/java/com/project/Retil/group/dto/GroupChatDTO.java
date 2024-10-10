package com.project.Retil.group.dto;

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
public class GroupChatDTO {

    @NotEmpty
    private Long user_id;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String userRank;

    @NotEmpty
    private String chat;

    @NotEmpty
    private LocalDateTime stampTime;

}