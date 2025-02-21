package org.wangpai.mathlab.builtin.factorial;

import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2022-11-20
 */
public class Factorial {
    /**
     * 计算 n!
     *
     * @since 2022-8-29
     */
    public static long factorial(final int n) {
        if (n < 0) {
            throw new LogicalException(String.format("错误：不能计算负数 %d 的阶乘", n));
        }
        if (n == 0 || n == 1) {
            return 1; // 数学规定：0!、1! = 1
        }
        long result = 1;
        for (int i = 2; i <= n; ++i) {
            result *= i;
        }
        return result;
    }
}
