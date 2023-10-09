package kr.firstspringtest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MyCalculatorRepeatableAndParameterTest {

    @RepeatedTest(5)
    @DisplayName(value = "덧셈을 RepeatedTest 숫자만큼 반복하여 테스트 --> 여러값을 테스트 불가능. --> 따라서 ParameterizedTest 사용")
    public void repeatedAddTest() {
        MyCalculator myCalculator = new MyCalculator();

        myCalculator.add(10.0);

        assertThat(myCalculator.getResult()).isEqualTo(10.0);
    }

    @DisplayName(value = """
        1. ParameterizedTest 로  @MethodSource 에서 명시한 메서드에서 리턴하는 Stream 길이만큼 테스트를 반복.
        2. @MethodSource 에서 명시한 메서드에서는 @ParameterizedTest 에서 사용하려는 Arguments 들을 넘겨줄 수 있음.
        3. @MethodSource 에서 명시한 메서드는 static 이어야 함. 
        4. @ParameterizedTest 에서는 ValueSource, EnumSource, CsvSource, CsvFileSource 등도 사용가능함.
    """)
    @ParameterizedTest
    @MethodSource("parameterizedTestParameters")
    public void parameterizedTest(Double addValue, Double expectValue) {
        MyCalculator myCalculator = new MyCalculator(0.0);

        myCalculator.add(addValue);

        assertThat(myCalculator.getResult()).isEqualTo(expectValue);
    }

    public static Stream<Arguments> parameterizedTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 10.0),
                Arguments.of(8.0, 8.0),
                Arguments.of(16.0, 16.0),
                Arguments.of(13.0, 13.0)
        );
    }

    @DisplayName(value =  "ParameterizedTest2 - 파라미터를 넣으며 사칙여산 2번 반복")
    @ParameterizedTest
    @MethodSource("parameterizedComplicatedCalculateTestParameters")
    public void parameterizedComplicatedCalculateTest(
            Double addValue,
            Double minusValue,
            Double multiplyValue,
            Double divideValue,
            Double expectValue
    ) {
        MyCalculator myCalculator = new MyCalculator();

        Double result = myCalculator
                .add(addValue)
                .minus(minusValue)
                .multiply(multiplyValue)
                .divide(divideValue)
                .getResult();

        assertThat(result).isEqualTo(expectValue);
    }

    public static Stream<Arguments> parameterizedComplicatedCalculateTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0),
                Arguments.of(4.0, 2.0, 4.0, 4.0, 2.0)
        );
    }
}
