package kr.firstspringtest.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveExamScoreRequest {
    private String studentName;
    private Integer korScore;
    private Integer englishScore;
    private Integer mathScore;
}
