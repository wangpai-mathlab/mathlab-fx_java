package org.wangpai.mathlab.advanced.compression;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;

/**
 * @since 2022-11-18
 */
class FigureCompressionTest {
    /**
     * @since 2022-11-18
     */
    @Test
    void uniformCompressWnc_FigureArray() {
        int num = 10;
        var figures = new Figure[num];

        int times = 100;
        for (int index = 1; index <= num; ++index) {
            figures[index - 1] = new Figure(index * times);
        }

        var result = FigureCompression.uniformlyWnc(
                figures,
                new Figure[]{Figure.ZERO, new Figure(num * times)},
                new int[]{1, num + 1});

        System.out.println(Arrays.toString(figures));
        System.out.println(Arrays.toString(result));
    }
}