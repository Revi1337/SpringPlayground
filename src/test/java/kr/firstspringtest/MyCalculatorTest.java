package kr.firstspringtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName(value = "강의에서는 Junit5 를 사용하지만 AssertJ 가 많이 사용되기 때문에 혼자 작성")
class MyCalculatorTest {

    @Test
    void add() {
        // AAA 패턴 (GWT 패턴과 동일)

        // Arrange - 준비 (Given)
        MyCalculator myCalculator = new MyCalculator();

        // Arrange - 행동 (When)
        myCalculator.add(10.0);

        // Assert - 단언 / 검증 (Then)
        assertThat(myCalculator.getResult()).isEqualTo(10.0);
    }

    @Test
    void minus() {
        MyCalculator myCalculator = new MyCalculator(10.0);

        myCalculator.minus(5.0);

        assertThat(myCalculator.getResult()).isEqualTo(5.0);
    }

    @Test
    void multiply() {
        MyCalculator myCalculator = new MyCalculator(2.0);

        myCalculator.multiply(2.0);

        assertThat(myCalculator.getResult()).isEqualTo(4.0);
    }

    @Test
    void divide() {
        MyCalculator myCalculator = new MyCalculator(10.0);

        myCalculator.divide(2.0);

        assertThat(myCalculator.getResult()).isEqualTo(5.0);
    }

    @Test
    void complicatedCalculateTest() {
        MyCalculator myCalculator = new MyCalculator();

        Double result = myCalculator
                .add(10.0)
                .minus(4.0)
                .multiply(2.0)
                .divide(3.0)
                .getResult();

        assertThat(result).isEqualTo(4.0);
    }

    @Test
     void  divideZeroTest() {
        MyCalculator myCalculator = new MyCalculator();

        assertThatThrownBy(() -> myCalculator.divide(0.0));

        assertThatThrownBy(() -> myCalculator.divide(0.0))
                .isInstanceOf(MyCalculator.ZeroDivisionException.class);
    }
}
