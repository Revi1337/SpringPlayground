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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito; // 이놈
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then; // given(), when(), then() 을 위해 이놈을 임포트 (org.mockito.Mockito 것을 사용할수도있지만 BDDMockito 가 더 편하고 많은 기능을 지원)
import static org.mockito.BDDMockito.given; // given(), when(), then() 을 위해 이놈을 임포트 (org.mockito.Mockito 것을 사용할수도있지만 BDDMockito 가 더 편하고 많은 기능을 지원)
import static org.mockito.BDDMockito.when; // given(), when(), then() 을 위해 이놈을 임포트 (org.mockito.Mockito 것을 사용할수도있지만 BDDMockito 가 더 편하고 많은 기능을 지원)

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

    @Test
    @DisplayName("합격자 명단 가져오기 검증 - 어떠한 Mock 객체의 리턴값을 미리 정의하고, 그러한 리턴값일 때, 우리의 로직은 ~하게 동작해야합니다라는 것을 검증할 수 있음.")
    public void getPassStudentsListTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        // TODO studentPassRepository 의 findAll() 를 호출한다면 무조건 아래 명시한 3개의 StudentPass 를 리턴해줄거야라는 것을 정의하는 "명령" 이다.
        //  when().thenReturn() 구문을 사용하면 정의한 리턴값을 고정해서 사용할 수 있게된다.
        String givenTestExam = "testexam";
        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("revi1337").exam(givenTestExam).avgScore(70.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam(givenTestExam).avgScore(80.0).build();
        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("iamnot").exam("secondexam").avgScore(90.0).build();
        when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository, studentPassRepository, studentFailRepository
        );

        // when
        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentsList(givenTestExam);
        responses.forEach(res -> System.out.println(res.getStudentName() + " " + res.getAvgScore()));

        // then
        assertThat(responses).extracting("studentName").containsExactly("revi1337", "test");
    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증 - 어떠한 Mock 객체의 리턴값을 미리 정의하고, 그러한 리턴값일 때, 우리의 로직은 ~하게 동작해야합니다라는 것을 검증할 수 있음.")
    public void getFailStudentsListTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        // TODO studentPassRepository 의 findAll() 를 호출한다면 무조건 아래 명시한 3개의 StudentPass 를 리턴해줄거야라는 것을 정의하는 "명령" 이다.
        //  when().thenReturn() 구문을 사용하면 정의한 리턴값을 고정해서 사용할 수 있게 된다.
        String givenTestExam = "testexam";
        StudentFail expectStudent1 = StudentFail.builder().id(1L).studentName("revi1337").exam(givenTestExam).avgScore(50.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(2L).studentName("test").exam(givenTestExam).avgScore(45.0).build();
        StudentFail notExpectStudent3 = StudentFail.builder().id(3L).studentName("iamnot").exam("secondexam").avgScore(35.0).build();
        when(studentFailRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository, studentPassRepository, studentFailRepository
        );

        // when
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentsList(givenTestExam);
        responses.forEach(res -> System.out.println(res.getStudentName() + " " + res.getAvgScore()));

        // then
        assertThat(responses).extracting("studentName").containsExactly("revi1337", "test");
    }

    @Test
    @DisplayName("""
            성적 저장 로직 검증 / 60 점 미만인 경우 -->  인자값 검증.
            ArgumentCaptor 를 통해 Mock 된 객체의 메서드가 실행될 때 검사할 매개변수를 뽑아와서 검사할 수 있음.
            ArgumentCaptor.forClass 를 통해 검사할 매개변수를 뽑아오면 꼭 capture() 를 통해 사용을해야함.
    """)
    public void checkArgumentTest() {
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

        StudentScore expectedStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();

        StudentPass expectedStudentPass = StudentPass.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        new MyCalculator(0.0)
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                ).build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // when (saveScore 를 호출했을 때)
        studentScoreService.saveScore(givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

        // then (studentScoreRepository, studentPassRepository, studentFailRepository 의 동작 및 행동을 검증한다. --> BDD)
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        assertThat(capturedStudentScore.getStudentName()).isEqualTo(expectedStudentScore.getStudentName());
        assertThat(capturedStudentScore.getExam()).isEqualTo(expectedStudentScore.getExam());
        assertThat(capturedStudentScore.getKorScore()).isEqualTo(expectedStudentScore.getKorScore());
        assertThat(capturedStudentScore.getEnglishScore()).isEqualTo(expectedStudentScore.getEnglishScore());
        assertThat(capturedStudentScore.getMathScore()).isEqualTo(expectedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
        assertThat(capturedStudentPass.getStudentName()).isEqualTo(expectedStudentPass.getStudentName());
        assertThat(capturedStudentPass.getExam()).isEqualTo(expectedStudentPass.getExam());
        assertThat(capturedStudentPass.getAvgScore()).isEqualTo(expectedStudentPass.getAvgScore());

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("""
            성적 저장 로직 검증 / 60 점 이상인 경우 -->  인자값 검증.
            ArgumentCaptor 를 통해 Mock 된 객체의 메서드가 실행될 때 검사할 매개변수를 뽑아와서 검사할 수 있음.
            ArgumentCaptor.forClass 를 통해 검사할 매개변수를 뽑아오면 꼭 capture() 를 통해 사용을해야함.
    """)
    public void checkArgumentTest2() {
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

        StudentScore expectedStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();

        StudentFail expectedStudentFail = StudentFail.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        new MyCalculator(0.0)
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                ).build();
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);


        // when (saveScore 를 호출했을 때)
        studentScoreService.saveScore(givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

        // then (studentScoreRepository, studentPassRepository, studentFailRepository 의 동작 및 행동을 검증한다. --> BDD)
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        assertThat(capturedStudentScore.getStudentName()).isEqualTo(expectedStudentScore.getStudentName());
        assertThat(capturedStudentScore.getExam()).isEqualTo(expectedStudentScore.getExam());
        assertThat(capturedStudentScore.getKorScore()).isEqualTo(expectedStudentScore.getKorScore());
        assertThat(capturedStudentScore.getEnglishScore()).isEqualTo(expectedStudentScore.getEnglishScore());
        assertThat(capturedStudentScore.getMathScore()).isEqualTo(expectedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());

        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        assertThat(capturedStudentFail.getStudentName()).isEqualTo(expectedStudentFail.getStudentName());
        assertThat(capturedStudentFail.getExam()).isEqualTo(expectedStudentFail.getExam());
        assertThat(capturedStudentFail.getAvgScore()).isEqualTo(expectedStudentFail.getAvgScore());
    }
}