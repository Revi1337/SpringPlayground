package kr.firstspringtest.service;

import kr.firstspringtest.MyCalculator;
import kr.firstspringtest.controller.response.ExamFailStudentResponse;
import kr.firstspringtest.controller.response.ExamPassStudentResponse;
import kr.firstspringtest.model.StudentFail;
import kr.firstspringtest.model.StudentPass;
import kr.firstspringtest.model.StudentScore;
import kr.firstspringtest.repository.StudentFailRepository;
import kr.firstspringtest.repository.StudentPassRepository;
import kr.firstspringtest.repository.StudentScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;

    public void saveScore(String studentName, String exam, Integer korScore, Integer englishScore, Integer mathScore) {
        StudentScore studentScore = StudentScore.builder()
                .exam(exam)
                .studentName(studentName)
                .korScore(korScore)
                .englishScore(englishScore)
                .mathScore(mathScore)
                .build();

        studentScoreRepository.save(studentScore);

        MyCalculator myCalculator = new MyCalculator(0.0);
        Double avgScore = myCalculator
                .add(korScore.doubleValue())
                .add(englishScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if (avgScore >= 60) {
            StudentPass studentPass = StudentPass.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScore(avgScore)
                    .build();
            studentPassRepository.save(studentPass);
        } else {
            StudentFail studentFail = StudentFail.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScore(avgScore)
                    .build();
            studentFailRepository.save(studentFail);
        }
    }

    public List<ExamPassStudentResponse> getPassStudentsList(String exam) {
        return studentPassRepository
                .findAll()
                .stream()
                .filter(pass -> pass.getExam().equals(exam))
                .map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .collect(Collectors.toList());
    }

    public List<ExamFailStudentResponse> getFailStudentsList(String exam) {
        return studentFailRepository
                .findAll()
                .stream()
                .filter(pass -> pass.getExam().equals(exam))
                .map(pass -> new ExamFailStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .collect(Collectors.toList());
    }



}
