package org.wangpai.mathlab.advanced.numeric.extend.radix;

import org.junit.jupiter.api.Test;
import org.wangpai.exception.checked.failed.OverflowException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTest {

    @Test
    void toLong() throws OverflowException {
        {
            var digits = new int[]{1, 1, 0, 1}; // 低位在前
            var binary = new Binary(digits);
            assertEquals(0b1101, binary.toLong());
        }
        {
            var digits = new int[]{1, 1, 0, 1}; // 低位在前
            var binary = new Binary(digits, true);
            var expected = -(new Binary(digits, false).toLong());
            assertEquals(expected, binary.toLong());
        }
    }
}