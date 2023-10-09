package kr.firstspringtest.controller.response;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExamPassStudentResponse {
    private String studentName;
    private Double avgScore;
}
