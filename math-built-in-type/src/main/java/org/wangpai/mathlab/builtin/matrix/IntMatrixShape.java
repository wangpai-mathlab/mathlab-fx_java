package org.wangpai.mathlab.builtin.matrix;

import java.util.Arrays;

/**
 * @since 2023-1-7
 */
public class IntMatrixShape {
    /**
     * 判断无重复数组 array 是否满足轴对称。判断依据是将这些数据点画在数轴上，如果这些数据点可以围绕一个点轴对称，则认为是
     *
     * @since 2023-1-7
     */
    public static boolean isAxialSymmetry(int[] array) {
        var dup = array.clone();
        Arrays.sort(dup);
        if (dup.length % 2 == 0) {
            int half = array.length / 2;
            int pairSum = dup[0] + dup[array.length - 1];
            for (int index = 1; index < half; index++) {
                if (dup[index] + dup[array.length - 1 - index] != pairSum) {
                    return false;
                }
            }
        } else {
            int half = array.length / 2;
            int pairSum = dup[half] * 2;
            // 下面的循环不能和下面的合并
            for (int index = 0; index < half; index++) {
                if (dup[index] + dup[array.length - 1 - index] != pairSum) {
                    return false;
                }
            }
        }
        return true;
    }
}
