package org.wangpai.mathlab.builtin.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntMatrixShapeTest {

    @Test
    void isAxialSymmetry() {
        assertTrue(IntMatrixShape.isAxialSymmetry(new int[]{1, 2, 3, 4, 5}));
        assertTrue(IntMatrixShape.isAxialSymmetry(new int[]{1, 2, 3, 4, 5, 6}));
        assertTrue(IntMatrixShape.isAxialSymmetry(new int[]{1, 2, 4, 5}));
        assertTrue(IntMatrixShape.isAxialSymmetry(new int[]{1, 2, 4, 6, 7}));
        
        assertFalse(IntMatrixShape.isAxialSymmetry(new int[]{1, 2, 4, 6, 8}));
    }
}