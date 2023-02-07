package csnojam.app.problem.dto;

import lombok.Data;

@Data
public class CreateProblemDto {
    private final Long categoryId;
    private final String text;
    private final String correct_answer;
}
