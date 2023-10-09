package kr.firstspringtest;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class LombokTestDataTest {

    @Test
    public void testDataTest() {
        TestData testData = new TestData();
        testData.setName("revi1337");

        assertThat(testData.getName()).isEqualTo("revi1337");
    }

}