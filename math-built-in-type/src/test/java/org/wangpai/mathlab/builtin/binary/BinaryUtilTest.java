package org.wangpai.mathlab.builtin.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryUtilTest {

    @Test
    void fractionToBinary() {
        assertEquals("100.0",
                BinaryUtil.fractionToBinary(4, 1, 8));
        assertEquals("0.00010101",
                BinaryUtil.fractionToBinary(1, 12, 8));
        assertEquals("0.00010101",
                BinaryUtil.fractionToBinary(2, 24, 8));
        assertEquals("100.00010101",
                BinaryUtil.fractionToBinary(49, 12, 8));
    }

    @Test
    void decimalToBinary() {
        assertEquals("100",
                BinaryUtil.decimalToBinary(4.0, 8));
        assertEquals("0.1",
                BinaryUtil.decimalToBinary(0.5, 8));
        assertEquals("0.00010101",
                BinaryUtil.decimalToBinary(1 / 12.0, 8));
        assertEquals("100.00010101",
                BinaryUtil.decimalToBinary(49 / 12.0, 8));
    }
}