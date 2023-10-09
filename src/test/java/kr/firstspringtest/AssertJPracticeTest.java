package kr.firstspringtest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName(value = "https://umanking.github.io/2021/06/26/assertj-iteration/")
public class AssertJPracticeTest {

    @Test
    public void isEqualToTest() {
        String expect = "Something";
        String actual = "Something";

        assertThat(expect).isEqualTo(actual);
    }

    @Test
    public void isNotEqualTo() {
        String expect = "Something";
        String actual = "Not Equal";

        assertThat(expect).isNotEqualTo(actual);
    }

    @Test
    public void isTrueTest() {
        Boolean condition = (1 == 1);

        assertThat(condition).isTrue();
    }

    @Test
    public void isFalseTest() {
        Boolean condition = (1 != 1);

        assertThat(condition).isFalse();
    }

    @Test
    public void isNullTest() {
        String dummy = null;

        assertThat(dummy).isNull();
    }

    @Test
    public void isNotNullTest() {
        String dummy = "STRING";

        assertThat(dummy).isNotNull();
    }

    @Test
    public void assertThatThrownByTest() {
        MyCalculator myCalculator = new MyCalculator();

        assertThatThrownBy(() -> myCalculator.divide(0.0))
                .isInstanceOf(MyCalculator.ZeroDivisionException.class);

        assertThatThrownBy(() -> { throw new MyCalculator.ZeroDivisionException(); });

        assertThatThrownBy(() -> { throw new RuntimeException("런타임 에러 발생"); })
                .hasMessage("런타임 에러 발생");

        assertThatThrownBy(() -> { throw new MyCalculator.ZeroDivisionException(); })
                .isInstanceOf(MyCalculator.ZeroDivisionException.class);

        assertThatNullPointerException().isThrownBy(() -> { throw new NullPointerException(); });
    }

    @Test
    public void iterableTest() {
        List<String> list1 = List.of("one", "two", "three");
        List<String> list2 = List.of("one", "two", "three");

        // 두 리스트의 값을 비교. (순서까지 정확해야함)
        assertThat(list1).containsExactlyElementsOf(list2);

        // 두 리스트의 사이즈 비교
        assertThat(list1).hasSameSizeAs(list2);

        // 특정 요소 탐색
        assertThat(list1).first().isEqualTo("one");
        assertThat(list1).element(1).isEqualTo("two");
        assertThat(list1).last().isEqualTo("three");

        // filter 사용
        assertThat(list1).filteredOn(string -> string.length() == 5)
                .containsExactly("three");

        // 모든 요소가 조건을 만족하는지
        assertThat(list1).allMatch(string -> !string.isEmpty());

        // 모든 요소가 여러 조건을 만족하는지 (noneSatisfy, anySatisfy 등이 있음.)
        assertThat(list1).allSatisfy(string -> {
            assertThat(string).isNotNull();
            assertThat(string).isNotEmpty();
        });

    }

}
