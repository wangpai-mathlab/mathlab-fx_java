package org.wangpai.mathlab.builtin.matrix;

import org.wangpai.commonutil.tc.builtin.BuiltInTc;

/**
 * @since 2022-12-24
 */
public class BooleanMatrixUtil {
    /**
     * 数组逆置
     *
     * 此方法会直接改变形参
     *
     * @since 2022-12-24
     */
    public static boolean[] reverse(boolean[] array) {
        for (int start = 0, end = array.length - 1; start < end; start++, end--) {
            var temp = array[start];
            array[start] = array[end];
            array[end] = temp;
        }
        return array;
    }

    /**
     * 二维矩阵转置
     *
     * @since 2022-12-3
     */
    public static boolean[][] matrixTranspose(boolean[][] twoDimensionalMatrix) {
        var formerRow = twoDimensionalMatrix.length;
        var formerColumn = twoDimensionalMatrix[0].length;
        var result = new boolean[formerColumn][formerRow];
        for (int rowIndex = 0; rowIndex < formerColumn; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < formerRow; ++columnIndex) {
                result[rowIndex][columnIndex] = twoDimensionalMatrix[columnIndex][rowIndex];
            }
        }
        return result;
    }

    /**
     * 将一维数组转化为字符串。这里会将 true 转化成 1，false 转化成 0
     *
     * @since 2022-12-24
     */
    public static String matrixToString(boolean[] matrix) {
        if (matrix == null) {
            return "null";
        }
        int iMax = matrix.length - 1;
        if (iMax == -1) {
            return "[]";
        }

        var sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; ; i++) {
            sb.append(BuiltInTc.boolean2int(matrix[i]));
            if (i == iMax) {
                return sb.append(']').toString();
            }
            sb.append(", ");
        }
    }
}
