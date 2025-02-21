package org.wangpai.mathlab.common;

/**
 * @since 2023-1-30
 */
public class DoubleUtil {
    /**
     * 进行 double 类型的相等比较
     *
     * @since 2023-1-30
     */
    public static boolean isEqual(double first, double second) {
        // 注意：double 类型不能直接用 == 来比较是否相等
        return Double.doubleToLongBits(first) == Double.doubleToLongBits(second);
    }
}
