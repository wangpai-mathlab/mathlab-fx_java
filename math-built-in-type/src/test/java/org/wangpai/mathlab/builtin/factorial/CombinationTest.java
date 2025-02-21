package org.wangpai.mathlab.builtin.factorial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @since 2022-11-20
 */
class CombinationTest {

    @Test
    void combination() {
        int n = 4;
        int m = 2;
        assertEquals(6, Combination.combination(n, m));
        assertEquals(n, Combination.combination(n, 1));
    }
}