package com.hr.microservices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Basic Junit Tests")
@Tag("dev")
public class JunitExampleTest {

    @Test
    public void demoMethod() {
        Assertions.assertTrue(true);
    }
}
