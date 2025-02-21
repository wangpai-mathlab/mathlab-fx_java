package org.wangpai.mathlab.builtin.matrix;

import java.util.Arrays;

/**
 * @since 2022-12-3
 */
public class IntMatrixUtil {
    /**
     * 数组逆置
     *
     * 此方法会直接改变形参
     *
     * @since 2022-12-24
     */
    public static int[] reverse(int[] array) {
        for (int start = 0, end = array.length - 1; start < end; start++, end--) {
            var temp = array[start];
            array[start] = array[end];
            array[end] = temp;
        }
        return array;
    }

    /**
     * 从数组中去掉下标为 index 的元素
     *
     * 这个方法不会改变形参
     *
     * @since 2022-12-25
     */
    public static int[] removeElement(int[] array, int index) {
        var result = new int[array.length - 1];
        System.arraycopy(array, 0, result, 0, index);
        System.arraycopy(array, index + 1, result, index, array.length - index - 1);
        return result;
    }

    /**
     * 二维矩阵转置
     *
     * @since 2022-12-3
     */
    public static int[][] matrixTranspose(int[][] twoDimensionalMatrix) {
        var formerRow = twoDimensionalMatrix.length;
        var formerColumn = twoDimensionalMatrix[0].length;
        var result = new int[formerColumn][formerRow];
        for (int rowIndex = 0; rowIndex < formerColumn; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < formerRow; ++columnIndex) {
                result[rowIndex][columnIndex] = twoDimensionalMatrix[columnIndex][rowIndex];
            }
        }
        return result;
    }

    /**
     * 将二维数组转化为字符串
     *
     * @since 2022-12-3
     */
    public static String matrixToString(int[][] matrix) {
        var result = new StringBuilder();
        for (var ele : matrix) {
            result.append(Arrays.toString(ele)).append(System.lineSeparator());
        }
        return result.toString();
    }
}
