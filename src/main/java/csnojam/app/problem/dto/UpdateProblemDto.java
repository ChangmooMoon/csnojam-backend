package csnojam.app.problem.dto;

import lombok.Data;

@Data
public class UpdateProblemDto {
    private final Long categoryId;
    private final String text;
    private final String correct_answer;
}
