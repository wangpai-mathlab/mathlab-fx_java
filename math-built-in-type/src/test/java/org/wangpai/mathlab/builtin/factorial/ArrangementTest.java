package org.wangpai.mathlab.builtin.factorial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @since 2022-11-20
 */
class ArrangementTest {

    @Test
    void arrangement() {
        int n = 4;
        int m = 2;
        assertEquals(12, Arrangement.arrangement(n, m));
        assertEquals(n, Arrangement.arrangement(n, 1));
    }
}