package com.hr.microservices;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParamsTest {

    final Set<String> fruits = Set.of("Apple","Banana","Orange");

    @ParameterizedTest
    @CsvSource({
            "4,4,8",
            "3,2,5",
            "1,2,4",
            "4,5,10"
    })
    public void testAddition(final int x, final int y, final int result) {
        Assertions.assertEquals(x + y, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
    public void testLessThan5(final int x) {
        Assertions.assertTrue(x <= 5);
    }

    @ParameterizedTest
    @MethodSource("fruitsData")
    public void testFruit(final String item) {
        Assertions.assertTrue(fruits.contains(item));
    }

    private Stream<String> fruitsData() {
        return Stream.of("Apple","Ball","Cat","Orange","Bus");
    }
}
