package com.codecool.tdd;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class MathEvaluatorTest {

    private MathEvaluator evaluator;

    @BeforeEach
    void setUp() {
        evaluator = new MathEvaluator();
    }

    @Test
    void smokeTest() {
        assertNotNull(evaluator);
    }

    @Test
    void parameterNull() {
        Assertions.assertThrows(ParameterNullException.class, () -> evaluator.evaluate(null));
    }

    @Test
    void parameterEmpty() {
        Assertions.assertThrows(ParameterEmptyException.class, () -> evaluator.evaluate(""));
    }

    @TestFactory
    Collection<DynamicTest> invalidParameter() {
        return asList(DynamicTest.dynamicTest("3 - a", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate("3 - a"))),
                                DynamicTest.dynamicTest("34.fg3", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate("34.fg3"))),
                                DynamicTest.dynamicTest("abcdefg", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate(".-3,  ! + 456778  ßqw"))),
                                DynamicTest.dynamicTest("3987f----21+23", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate("3987f----21+23"))),
                                DynamicTest.dynamicTest("3. +6", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate("3. +6"))),
                                DynamicTest.dynamicTest(".3 +6", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate(".3 +6"))),
                                DynamicTest.dynamicTest("3 +6.", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate("3 +6."))),
                                DynamicTest.dynamicTest("3  . 6", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate("3  . 6"))),
                                DynamicTest.dynamicTest(",+4", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate(",+4"))),
                                DynamicTest.dynamicTest(" et 56 - 33  -/", () -> assertThrows(ParameterInvalidException.class, () -> evaluator.evaluate(" et 56 - 33  -/"))));

    }

    @TestFactory
    Collection<DynamicTest> validParameter() {
        return asList(DynamicTest.dynamicTest("3", () -> assertEquals(3.0, evaluator.evaluate("3"))),
                                DynamicTest.dynamicTest("3 - 2.5", () -> assertEquals(0.5, evaluator.evaluate("3 - 2.5"))),
                                DynamicTest.dynamicTest("3    -   2.5", () -> assertEquals(0.5, evaluator.evaluate("3    -   2.5"))),
                                DynamicTest.dynamicTest("1 + 2 * 3", () -> assertEquals(7, evaluator.evaluate("1 + 2 * 3"))),
                                DynamicTest.dynamicTest("3.4", () -> assertEquals(3.4, evaluator.evaluate("3.4"))),
                                DynamicTest.dynamicTest("4/3 + 5 - 1 * 2", () -> assertEquals(4.33333, evaluator.evaluate("4/3 + 5 - 1 * 2"))));
    }

    @TestFactory
    Collection<DynamicTest> expressionToListTest() {
        List<String> a = new ArrayList<>(asList("3"));
        List<String> b = new ArrayList<>(asList("3","-","2.5"));
        List<String> c = new ArrayList<>(asList("1","+","2","*","3"));
        List<String> d = new ArrayList<>(asList("3.4"));
        List<String> e = new ArrayList<>(asList("4","/","3","+","5","-","1","*","2"));
        return asList(DynamicTest.dynamicTest("3", () -> assertIterableEquals(a, evaluator.expressionToList("3"))),
                                DynamicTest.dynamicTest("3-2.5", () -> assertIterableEquals(b, evaluator.expressionToList("3-2.5"))),
                                DynamicTest.dynamicTest("1+2*3", () -> assertIterableEquals(c, evaluator.expressionToList("1+2*3"))),
                                DynamicTest.dynamicTest("3.4", () -> assertIterableEquals(d, evaluator.expressionToList("3.4"))),
                                DynamicTest.dynamicTest("4/3+5-1*2", () -> assertIterableEquals(e, evaluator.expressionToList("4/3+5-1*2"))));
    }
}