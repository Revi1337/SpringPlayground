package kr.firstspringtest.model;

import kr.firstspringtest.MyCalculator;

public class StudentFailFixture {

    public static StudentFail create(StudentScore studentScore) {
        MyCalculator myCalculator = new MyCalculator();
        return StudentFail
                .builder()
                .exam(studentScore.getExam())
                .studentName(studentScore.getStudentName())
                .avgScore(
                        myCalculator
                                .add(studentScore.getKorScore().doubleValue())
                                .add(studentScore.getEnglishScore().doubleValue())
                                .add(studentScore.getMathScore().doubleValue())
                                .divide(3.0)
                                .getResult())
                .build();
    }

    public static StudentFail create(String studentName, String exam) {
        return StudentFail
                .builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(80.0)
                .build();
    }
}
