package org.wangpai.mathlab.builtin.primitive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimitiveMathTest {

    @Test
    void pow() {
        assertEquals(1024, PrimitiveMath.pow(2, 10));
    }

    @Test
    void divideWithCeil() {
        assertEquals(1, PrimitiveMath.divideWithCeil(2, 10));
        assertEquals(2, PrimitiveMath.divideWithCeil(12, 10));
        assertEquals(6, PrimitiveMath.divideWithCeil(12, 2));
    }

    @Test
    void findClosest2Exponent() {
        assertEquals(0, PrimitiveMath.findClosest2Exponent(1, true));
        assertEquals(3, PrimitiveMath.findClosest2Exponent(5, true));
        assertEquals(4, PrimitiveMath.findClosest2Exponent(10, true));
        assertEquals(4, PrimitiveMath.findClosest2Exponent(15, true));
        assertEquals(4, PrimitiveMath.findClosest2Exponent(16, true));
        assertEquals(5, PrimitiveMath.findClosest2Exponent(20, true));

        assertEquals(0, PrimitiveMath.findClosest2Exponent(1, false));
        assertEquals(2, PrimitiveMath.findClosest2Exponent(5, false));
        assertEquals(3, PrimitiveMath.findClosest2Exponent(10, false));
        assertEquals(3, PrimitiveMath.findClosest2Exponent(15, false));
        assertEquals(4, PrimitiveMath.findClosest2Exponent(16, false));
        assertEquals(4, PrimitiveMath.findClosest2Exponent(20, false));
    }

    @Test
    void findClosest2BaseInteger() {
        assertEquals(1, PrimitiveMath.findClosest2BaseInteger(1, true));
        assertEquals(8, PrimitiveMath.findClosest2BaseInteger(5, true));
        assertEquals(16, PrimitiveMath.findClosest2BaseInteger(10, true));
        assertEquals(16, PrimitiveMath.findClosest2BaseInteger(15, true));
        assertEquals(32, PrimitiveMath.findClosest2BaseInteger(20, true));
        assertEquals(32, PrimitiveMath.findClosest2BaseInteger(32, true));

        assertEquals(1, PrimitiveMath.findClosest2BaseInteger(1, false));
        assertEquals(4, PrimitiveMath.findClosest2BaseInteger(5, false));
        assertEquals(8, PrimitiveMath.findClosest2BaseInteger(10, false));
        assertEquals(8, PrimitiveMath.findClosest2BaseInteger(15, false));
        assertEquals(16, PrimitiveMath.findClosest2BaseInteger(20, false));
        assertEquals(32, PrimitiveMath.findClosest2BaseInteger(32, false));
    }
}