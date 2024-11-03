package com.hr.microservices;

import com.hr.microservices.service.HRCalculator;
import org.junit.jupiter.api.*;

@DisplayName("Calculator Tests")
public class CalculatorTest {
    HRCalculator hrCalculator;

    @BeforeEach
    public void CalculatorTest() {
        this.hrCalculator = new HRCalculator();
    }

    @Test
    public void testFailure() {
        Assertions.assertNotEquals(22, hrCalculator.mul(5, 4));
    }

    @Test
    @Tag("dev")
    public void testSuccess() {
        Assertions.assertEquals(20, hrCalculator.mul(5, 4));
    }
}
