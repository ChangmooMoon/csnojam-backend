package csnojam.app.problem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProblemDto {
    private final Long categoryId;
    private final String text;
    private final String correct_answer;
}
