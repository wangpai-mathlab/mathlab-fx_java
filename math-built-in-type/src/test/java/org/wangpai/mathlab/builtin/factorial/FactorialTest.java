package org.wangpai.mathlab.builtin.factorial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @since 2022-11-20
 */
class FactorialTest {

    @Test
    void factorial() {
        assertEquals(24, Factorial.factorial(4));
    }
}