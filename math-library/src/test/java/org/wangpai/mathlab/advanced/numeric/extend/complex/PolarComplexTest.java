package org.wangpai.mathlab.advanced.numeric.extend.complex;

import org.junit.jupiter.api.Test;

class PolarComplexTest {

    @Test
    void getInstanceByRect() {
        {
            var result = PolarComplex.getInstanceByRect(1, 0);
            System.out.println();
        }
        {
            var result = PolarComplex.getInstanceByRect(0, 1);
            System.out.println();
        }
        {
            var result = PolarComplex.getInstanceByRect(-1, 0);
            System.out.println();
        }
        {
            var result = PolarComplex.getInstanceByRect(0, -1);
            System.out.println();
        }
    }
}