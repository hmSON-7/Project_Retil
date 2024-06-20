package com.project.Retil.review.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListDTO {

    @NotEmpty
    private Long review_id;

    @NotEmpty
    private LocalDate date;

    @NotEmpty
    private List<String> tilList;

    @NotEmpty
    private boolean flag;

}
