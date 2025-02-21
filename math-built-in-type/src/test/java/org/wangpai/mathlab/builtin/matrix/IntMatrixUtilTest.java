package org.wangpai.mathlab.builtin.matrix;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @since 2022-12-3
 */
class IntMatrixUtilTest {
    /**
     * @since 2022-12-3
     */
    @Test
    void matrixTranspose() {
        var matrix = new int[][]{
                new int[]{1, 2, 3, 4, 5},
                new int[]{11, 12, 13, 14, 15},
                new int[]{21, 22, 23, 24, 25},
                new int[]{31, 32, 33, 34, 35},
                new int[]{41, 42, 43, 44, 45},
        };
        System.out.println(IntMatrixUtil.matrixToString(matrix));
        System.out.println(IntMatrixUtil.matrixToString(IntMatrixUtil.matrixTranspose(matrix)));
    }

    /**
     * @since 2022-12-3
     */
    @Test
    void matrixToString() {
        var matrix = new int[][]{
                new int[]{1, 2, 3, 4, 5},
                new int[]{11, 12, 13, 14, 15},
                new int[]{21, 22, 23, 24, 25},
                new int[]{31, 32, 33, 34, 35},
                new int[]{41, 42, 43, 44, 45},
        };
        System.out.println(IntMatrixUtil.matrixToString(matrix));
    }

    @Test
    void reverse() {
        int[] array = {1, 5, 6, 8, 9, 4, 3};
        IntMatrixUtil.reverse(array);

        System.out.println();
    }

    @Test
    void removeElement() {
        var array = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        var result = IntMatrixUtil.removeElement(array, 5);
        System.out.println(Arrays.toString(result));
    }
}