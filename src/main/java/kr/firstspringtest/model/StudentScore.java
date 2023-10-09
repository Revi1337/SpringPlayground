package kr.firstspringtest.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "student_score")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_score_id")
    private Long id;

    @Column(name = "exam")
    private String exam;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "kor_score")
    private Integer korScore;

    @Column(name = "english_score")
    private Integer englishScore;

    @Column(name = "math_score")
    private Integer mathScore;
}
