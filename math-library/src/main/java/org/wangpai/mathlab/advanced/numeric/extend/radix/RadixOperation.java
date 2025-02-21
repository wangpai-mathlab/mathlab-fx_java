package org.wangpai.mathlab.advanced.numeric.extend.radix;

import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.exception.unchecked.dev.DevelopingException;

/**
 * @since 2022-11-17
 */
public class RadixOperation {
    /**
     * 对传入的形参使用以自身进制数为底数的对数运算
     *
     * 规定：log 0 = int 类型的最小值
     *
     * @param intervalNum 返回结果被分割成的区间数。此值相当于规定结果的精确度，此值越大，精确度越高
     * @since 2022-11-17
     */
    public static int logRadix(Radix variable, int intervalNum) {
        if (variable.isZero()) {
            return Integer.MIN_VALUE;
        }
        /**
         * 为了便于说明，设 variable 为 x, variable.digits.length 为 n。intervalNum 为 k。
         * log(r, x) 代表以 r 为底，x 的对数
         *
         * 如果 x 不为 0，那么 log(r, x)的范围为 [0, n)。将其分成 k 份，每个区间的长度是 n/k，
         * 即 [0, n/k),[n/k, 2n/k),...,[n(k-1)/k, n)。取这些区间的中值作为这些区间的值，
         * 即 n/2k, n/k + n/2k, 2n/k + n/2k,...,n(k-1)/k + n/2k。
         * 化简即为 n/2k, 3n/2k, 5n/2k, (2k - 1)n/2k，从而第 i 个区间的中值是 (2i - 1)n/2k
         */
        throw new DevelopingException("此方法还未完成编写，无法使用"); // FIXME：2022年11月17日：此方法待完成
    }

    /**
     * 将 radix 的绝对值增加 offset 值
     *
     * @since 2022-12-24
     */
    public static Radix increaseRadixAbsValue(Radix radix, long offset) throws LogicalException {
        throw new DevelopingException("此方法还未完成编写，无法使用"); // FIXME：2022年12月24日：此方法待完成
    }
}
