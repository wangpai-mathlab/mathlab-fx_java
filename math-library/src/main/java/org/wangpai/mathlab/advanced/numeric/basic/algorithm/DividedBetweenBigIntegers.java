package org.wangpai.mathlab.advanced.numeric.basic.algorithm;

import lombok.AllArgsConstructor;
import org.wangpai.mathlab.advanced.android.BigIntegerAdapter;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;

/**
 * 本类因为不支持在安卓中使用，现已暂停使用
 *
 * @since 2022-8-25
 */
public class DividedBetweenBigIntegers {
    /**
     * 大整数相除防溢出快速算法（一层）。将两个大整数相除，并把结果转化为 double 类型。使用本方法前，必须保证形参均为非负数
     *
     * 其中，rational 的分子、分母可以各自很大
     *
     * @deprecated 2024-8-30 已有 dividedBetweenBigIntegers 方法，所以不需要此方法了
     * @since 2022-8-25
     */
    @Deprecated
    public static double rational2doubleQuickly(Rational rational) {
        Figure dividend = rational.getNumeratorUnsafely(); // dividend：被除数
        Figure divisor = rational.getDenominatorUnsafely(); // divisor：除数
        final long maxLong = Long.MAX_VALUE;
        final Figure max = new Figure(maxLong);
        if (FigureOperation.greaterOrEqual(dividend, max) || FigureOperation.greaterOrEqual(divisor, max)) {
            Figure[] a1 = FigureOperation.divideAndRemainder(dividend, max);
            long b1 = a1[0].getOriginBigUnsafely().longValueExact();
            double c1 = (double) (a1[1].getOriginBigUnsafely().longValueExact());

            Figure[] a2 = FigureOperation.divideAndRemainder(divisor, max);
            long b2 = a2[0].getOriginBigUnsafely().longValueExact();
            double c2 = (double) (a2[1].getOriginBigUnsafely().longValueExact());

            return (b1 + c1 / maxLong) / (b2 + c2 / maxLong);
        } else {
            // 将分子、分母中较大的那个数转换为类型 double 来运算，因为 double 比 long 的范围大
            if (rational.isProperFraction()) {
                return rational.getNumeratorUnsafely().getOriginBigUnsafely().longValueExact()
                        / (double) (rational.getDenominatorUnsafely().getOriginBigUnsafely().longValueExact());
            } else {
                return (double) (rational.getNumeratorUnsafely().getOriginBigUnsafely().longValueExact())
                        / rational.getDenominatorUnsafely().getOriginBigUnsafely().longValueExact();
            }
        }
    }

    /**
     * 大整数相除防溢出递归算法。将两个大整数相除，并把结果转化为 double 类型。使用本方法前，必须保证形参均为非负数
     *
     * 计算 (a1 + b1) / (a2 + b2)。其中，a1、a2 为大数，b1、b2 均小于 max
     *
     * 本递归版可以计算位数更多的大数相除
     *
     * @since 2022-8-25
     */
    public static double dividedBetweenBigIntegers(Figure a1, double b1, Figure a2, double b2) {
        if (FigureOperation.greaterOrEqual(a1, Figure.LONG_MAX_VALUE)
                || FigureOperation.greaterOrEqual(a2, Figure.LONG_MAX_VALUE)) {
            SplitResult numerator = divideAndSplit(a1, b1);
            SplitResult denominator = divideAndSplit(a2, b2);
            return dividedBetweenBigIntegers(numerator.c, numerator.d, denominator.c, denominator.d);
        } else {
            // 只要 a1、a2 都比 Long.MAX_VALUE 要小，就可以直接运算
            return plus(a1, b1) / plus(a2, b2);
        }
    }

    /**
     * 计算 a + b。使用此方法前，必须保证 a 比 Long.MAX_VALUE 要小
     *
     * @since 2024-8-30
     */
    private static double plus(Figure a, double b) {
        double numerator;
        if (a.underlyingIsInt()) {
            numerator = a.getOriginSmallUnsafely() + b;
        } else {
            // 因为 a 比 Long.MAX_VALUE 要小，所以 longValueExact() 方法是不会溢出的
            numerator = BigIntegerAdapter.longValueExactBelowAndroid12(a.getOriginBigUnsafely()) + b;
            // BigInteger.longValueExact() 在安卓 12 才开始支持，因此暂不使用该方法
//            numerator = a.getOriginBigUnsafely().longValueExact() + b;
        }
        return numerator;
    }

    /**
     * 使用此方法前，必须保证 a 比 Long.MAX_VALUE 要小。
     * 算法主要做的事情：计算 (a + b) / m，然后将计算结果拆成 c 和 d 返回。即 (a + b) / m = c + d
     *
     * @since 2024-8-30
     */
    private static SplitResult divideAndSplit(Figure a, double b) {
        Figure[] aGroup = FigureOperation.divideAndRemainder(a, Figure.LONG_MAX_VALUE);
        // aGroup[0] 为对 Long.MAX_VALUE 的余数，所以 aGroup[0] 一定比 Long.MAX_VALUE 要小
        return new SplitResult(aGroup[0], plus(aGroup[1], b) / Long.MAX_VALUE);
    }

    /**
     * 因为安卓不支持 record 类，所以使用了本类作为替代
     *
     * @since 2024-8-30
     */
    @AllArgsConstructor
    private static class SplitResult {
        public final Figure c;
        public final double d;
    }

    /**
     * 本类因为不支持在安卓中使用，现已暂停使用
     *
     * @since 2024-8-30
     */
//    private static record SplitResult(Figure c, double d) {
//    }
}
