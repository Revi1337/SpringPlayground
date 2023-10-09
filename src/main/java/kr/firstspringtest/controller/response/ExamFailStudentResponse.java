package kr.firstspringtest.controller.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExamFailStudentResponse {
    private String studentName;
    private Double avgScore;
}
