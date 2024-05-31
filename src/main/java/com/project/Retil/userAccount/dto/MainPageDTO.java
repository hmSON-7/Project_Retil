package com.project.Retil.userAccount.dto;

import com.project.Retil.til.dto.TilListDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainPageDTO {

    @NotEmpty
    @Size(max = 86400000)
    private Long todayStudied;

    @NotEmpty
    private Long totalStudied;

    @NotEmpty
    private String userRank;

    @NotEmpty
    private ArrayList<TilListDTO> tilList;

}
