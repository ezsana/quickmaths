package com.codecool.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}