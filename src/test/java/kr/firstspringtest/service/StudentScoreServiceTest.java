package kr.firstspringtest.service;

import kr.firstspringtest.repository.StudentFailRepository;
import kr.firstspringtest.repository.StudentPassRepository;
import kr.firstspringtest.repository.StudentScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito; // 이놈


class StudentScoreServiceTest {

    @Test
    @DisplayName(value = "첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );
        String givenStudentName = "revi1337";
        String givenExam = "textexam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60 점 이상인 경우")
    public void saveScoreMockTest() {
        // given (평균 점수가 60 점 이상인 경우 - 해당 상황들이 주어졌을 때)
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        StudentScoreService studentScoreService = new StudentScoreService(
                        studentScoreRepository, studentPassRepository, studentFailRepository
        );
        String givenStudentName = "revi1337";
        String givenExam = "textexam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when (saveScore 를 호출했을 때)
        studentScoreService.saveScore(givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

        // then (studentScoreRepository, studentPassRepository, studentFailRepository 의 동작 및 행동을 검증한다. --> BDD)
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60 점 미만인 경우")
    public void saveScoreMockTest2() {
        // given (평균 점수가 60 점 미만인 경우 - 해당 상황들이 주어졌을 때)
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository, studentPassRepository, studentFailRepository
        );
        String givenStudentName = "revi1337";
        String givenExam = "textexam";
        Integer givenKorScore = 40;
        Integer givenEnglishScore = 40;
        Integer givenMathScore = 60;

        // when (saveScore 를 호출했을 때)
        studentScoreService.saveScore(givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

        // then (studentScoreRepository, studentPassRepository, studentFailRepository 의 동작 및 행동을 검증한다. --> BDD)
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
    }

}