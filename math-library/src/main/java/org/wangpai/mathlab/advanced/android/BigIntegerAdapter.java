package org.wangpai.mathlab.advanced.android;

import java.math.BigInteger;

/**
 * 一种用于替代安卓低版本不支持的关于 BigInteger 的 API
 *
 * @since 2024-8-30
 */
public class BigIntegerAdapter {
    /**
     * 因为安卓 12 及以上才支持 BigInteger.longValueExact()，所以创建了使用此替代方法
     *
     * @since 2024-8-30
     */
    public static long longValueExactBelowAndroid12(BigInteger bigInteger) throws ArithmeticException {
        if (bigInteger.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) >= 0
                && bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0) {
            return bigInteger.longValue();
        } else {
            throw new ArithmeticException();
        }
    }
}
