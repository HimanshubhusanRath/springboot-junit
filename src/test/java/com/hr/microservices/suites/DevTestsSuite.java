package com.hr.microservices.suites;

import com.hr.microservices.CalculatorTest;
import com.hr.microservices.JunitExampleTest;
import com.hr.microservices.UserControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({CalculatorTest.class, JunitExampleTest.class, UserControllerTest.class})
@DisplayName("Test Suite only for junit tests with 'dev' tag")
@IncludeTags("dev")
// @ExcludeTags("dev")
public class DevTestsSuite {

}
