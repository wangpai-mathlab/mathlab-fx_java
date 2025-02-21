package org.wangpai.mathlab.builtin.matrix;

import org.junit.jupiter.api.Test;

class BooleanMatrixUtilTest {

    @Test
    void matrixToString() {
        var binary = new boolean[]{true, true, false, true};
        System.out.println(BooleanMatrixUtil.matrixToString(binary));
    }
}