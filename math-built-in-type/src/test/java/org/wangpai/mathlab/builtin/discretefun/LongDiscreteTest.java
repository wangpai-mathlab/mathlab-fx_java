package org.wangpai.mathlab.builtin.discretefun;

import org.junit.jupiter.api.Test;

class LongDiscreteTest {
    @Test
    void difference() {
        var discrete = LongDiscrete.getInstance(new int[]{1, 4, 9, 16, 25, 36});
        var result = discrete.difference();
        System.out.println();
    }

    @Test
    void difference_int() {
        int rank = 10;
        int eleNum = rank * 2;
        var data = new long[eleNum];
        for (int i = 0; i < eleNum; i++) {
            data[i] = (long) Math.pow(i, rank);
        }
        var discrete = LongDiscrete.getInstance(data);
        var result = discrete.difference(rank - 1);
        System.out.println();
    }
}