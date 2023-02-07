package csnojam.app.problem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Problem {
    private Long categoryId;
    private String question;
    private String answer;
    private Integer view;
}
