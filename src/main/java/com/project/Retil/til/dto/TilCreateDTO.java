package com.project.Retil.til.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilCreateDTO {
    @NotEmpty
    private String subjectName;

    @NotEmpty
    private String title;

    @NotEmpty
    @Size(min = 5)
    private String content;
}
