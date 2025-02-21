package org.wangpai.mathlab.builtin.primitive;

import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.exception.unchecked.LogicalException;

/**
 * 提供一些与 Java 基本类型相关的基本数学运算
 *
 * @since 2022-11-10
 */
public class PrimitiveMath {
    /**
     * 计算整数的正整数乘方
     *
     * 本函数使用了快速幂算法，可见：https://leetcode.cn/problems/powx-n/solution/50-powx-n-kuai-su-mi-qing-xi-tu-jie-by-jyd/
     *
     * @param positiveExponent 指数，需要是非负数，可以为 0
     * @since 2022-11-10
     */
    public static long pow(int base, int positiveExponent) {
        if (positiveExponent < 0) {
            throw new ForbiddenCallingException("本方法不支持负数次幂");
        }
        long exponent = positiveExponent; // 此处必须进行这个转换，否则后面右移时可能会发生反向溢出
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) { // 如果 exponent 的二进制最低位不为 0
                result *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        return result;
    }

    /**
     * 计算整数之间的除法，并将结果向上取整（默认的整数除法是向下取整的，所以无需额外提供函数）
     *
     * 计算结果公式：(x + y - 1) / y
     *
     * @since 2023-1-19
     */
    public static long divideWithCeil(long dividend, long divisor) {
        if (divisor == 0) {
            throw new LogicalException("错误：0 不能作除数");
        }
        return (dividend + divisor - 1) / divisor;
    }

    /**
     * - 如果 isUpper 为 true，代表计算使 2^x >= n 成立的最小整数 x
     * - 如果 isUpper 为 false，代表计算使 2^x <= n 成立的最大整数 x
     *
     * @since 2023-5-27
     */
    public static int findClosest2Exponent(double n, boolean isUpper) {
        if (n <= 0) {
            throw new LogicalException("n 不能小于等于 0");
        }
        double result = Math.log(n) / Math.log(2);
        if (isUpper) {
            return (int) Math.ceil(result);
        } else {
            return (int) Math.floor(result);
        }
    }

    /**
     * - 如果 isUpper 为 true，代表计算使 2^x >= n 成立的最小整数 x，然后返回 2^x 的结果
     * - 如果 isUpper 为 false，代表计算使 2^x <= n 成立的最大整数 x，然后返回 2^x 的结果
     *
     * @since 2023-5-27
     */
    public static long findClosest2BaseInteger(double n, boolean isUpper) {
        var exponent = findClosest2Exponent(n, isUpper);
        return pow(2, exponent);
    }
}
