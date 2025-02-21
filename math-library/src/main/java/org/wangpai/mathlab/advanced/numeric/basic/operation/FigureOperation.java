package org.wangpai.mathlab.advanced.numeric.basic.operation;

import java.math.BigInteger;
import org.wangpai.exception.checked.failed.OverflowException;
import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.exception.unchecked.UnexpectedException;
import org.wangpai.logfx.Logfx;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;

/**
 * 注意：BigInteger 的加、减、乘 等方法有可能不会返回一个新的对象，它们的这些方法会依情况决定要不要返回新的对象
 *
 * @since 2021-8-2
 */
public final class FigureOperation {
    /*---------------加减乘---------------*/

    /**
     * 加法
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2021-8-5
     * @lastModified 2022-9-9
     */
    public static Figure add(Figure first, Figure second) {
        if (!first.underlyingIsInt() || !second.underlyingIsInt()) {
            return new Figure(first.toBigInteger().add(second.toBigInteger()));
        }

        long firstLong;
        long secondLong;
        try {
            firstLong = first.toInt();
            secondLong = second.toInt();
        } catch (OverflowException exception) { // 此异常应该是不会发生的
            Logfx.error("错误：发生了意料之外的异常。", exception);
            throw new UnexpectedException(exception);
        }
        return new Figure(firstLong + secondLong); // 两个原本为 int 类型的 long 相加是不会溢出的
    }

    /**
     * 加法
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 此方法只应在特殊情况下使用。一般进行 int 类型的加法，直接使用内置运算符运算即可，不应该使用本方法
     *
     * @since 2022-10-26
     */
    public static Figure add(int first, int second) {
        return new Figure((long) first + (long) second); // 两个原本为 int 类型的 long 相加是不会溢出的
    }

    /**
     * 减法
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2021-8-5
     * @lastModified 2022-9-9
     */
    public static Figure subtract(Figure first, Figure second) {
        if (!first.underlyingIsInt() || !second.underlyingIsInt()) {
            return new Figure(first.toBigInteger().subtract(second.toBigInteger()));
        }

        long firstLong;
        long secondLong;
        try {
            firstLong = first.toInt();
            secondLong = second.toInt();
        } catch (OverflowException exception) { // 此异常应该是不会发生的
            Logfx.error("错误：发生了意料之外的异常。", exception);
            throw new UnexpectedException(exception);
        }
        return new Figure(firstLong - secondLong); // 两个原本为 int 类型的 long 相减是不会溢出的
    }

    /**
     * 减法
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 此方法只应在特殊情况下使用。一般进行 int 类型的减法，直接使用内置运算符运算即可，不应该使用本方法
     *
     * @since 2022-10-26
     */
    public static Figure subtract(int first, int second) {
        return new Figure((long) first - (long) second); // 两个原本为 int 类型的 long 相减是不会溢出的
    }

    /**
     * 乘法
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2021-8-5
     * @lastModified 2022-9-9
     */
    public static Figure multiply(Figure first, Figure second) {
        if (!first.underlyingIsInt() || !second.underlyingIsInt()) {
            return new Figure(first.toBigInteger().multiply(second.toBigInteger()));
        }

        long firstLong;
        long secondLong;
        try {
            firstLong = first.toInt();
            secondLong = second.toInt();
        } catch (OverflowException exception) { // 此异常应该是不会发生的
            Logfx.error("错误：发生了意料之外的异常。", exception);
            throw new UnexpectedException(exception);
        }
        return new Figure(firstLong * secondLong); // 两个原本为 int 类型的 long 相乘是不会溢出的
    }

    /**
     * 乘法
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2024-9-2
     */
    public static Figure multiply(Figure first, int second) {
        if (first.underlyingIsInt()) {
            long firstLong;
            try {
                firstLong = first.toInt();
            } catch (OverflowException exception) { // 此异常应该是不会发生的
                Logfx.error("错误：发生了意料之外的异常。", exception);
                throw new UnexpectedException(exception);
            }
            return new Figure(firstLong * second); // 两个原本为 int 类型的 long 相乘是不会溢出的
        } else {
            return new Figure(first.toBigInteger().multiply(BigInteger.valueOf(second)));
        }
    }

    /**
     * 乘法
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 此方法只应在特殊情况下使用。一般进行 int 类型的乘法，直接使用内置运算符运算即可，不应该使用本方法
     *
     * @since 2022-10-26
     */
    public static Figure multiply(int first, int second) {
        return new Figure((long) first * (long) second); // 两个原本为 int 类型的 long 相乘是不会溢出的
    }

    /**
     * 除法。此方法禁止使用
     *
     * 占位空方法
     *
     * @since before 2021-8-5
     */
    @Deprecated
    public final static Figure divide(Figure dividend, Figure divisor) {
        throw new ForbiddenCallingException("错误：整数不支持除法运算");
    }

    /****************加减乘****************/

    /**
     * @return 返回的数组中，0 号元素代表商，1 号元素代表余数
     */
    public static Figure[] divideAndRemainder(Figure dividend, Figure divisor) {
        BigInteger[] quotientAndRemainder = dividend.toBigInteger()
                .divideAndRemainder(divisor.toBigInteger());
        Figure[] result = new Figure[quotientAndRemainder.length];
        for (int index = 0; index < quotientAndRemainder.length; ++index) {
            result[index] = new Figure(quotientAndRemainder[index]);
        }
        return result;
    }

    public static Figure[] divideAndRemainder(Figure dividend, long divisor) {
        return divideAndRemainder(dividend, new Figure(divisor));
    }

    /**
     * @return 返回求余数运算得到的余数
     */
    public static Figure mod(Figure dividend, Figure divisor) {
        final int REMAINDER_INDEX = 1;
        return divideAndRemainder(dividend, divisor)[REMAINDER_INDEX];
    }

    public static Figure mod(Figure dividend, long divisor) {
        return mod(dividend, new Figure(divisor));
    }

    /**
     * modsQuotient：mod's Quotient 求余数的商
     *
     * @return 返回求余数运算得到的商
     */
    public static Figure modsQuotient(Figure dividend, Figure divisor) {
        final int QUOTIENT_INDEX = 0;
        return divideAndRemainder(dividend, divisor)[QUOTIENT_INDEX];
    }

    public static Figure modsQuotient(Figure first, long second) {
        return modsQuotient(first, new Figure(second));
    }

    /**
     * 求相反数
     *
     * @since before 2021-8-5
     * @lastModified 2024-8-28
     */
    public static Figure getOpposite(Figure num) {
        if (num.underlyingIsInt()) {
            return new Figure(-num.getOriginSmallUnsafely());
        } else {
            // BigInteger.negate() 会返回一个新对象，所以使用 Figure.absorbBigInteger 能保证安全
            return Figure.absorbBigInteger(num.getOriginBigUnsafely().negate());
        }
    }

    /**
     * @since before 2021-8-5
     */
    public static Figure getOpposite(long num) {
        return getOpposite(new Figure(num));
    }

    /**
     * 求绝对值
     *
     * @since 2022-8-23
     */
    public static Figure getAbsolute(Figure num) {
        if (num.isNegative()) {
            return getOpposite(num);
        } else {
            return num.clone();
        }
    }

    /**
     * 小于：first < second
     *
     * @since 2022-8-29 v0
     * @lastModified 2022-12-23 v2.1
     */
    public static boolean lessThan(Figure first, Figure second) {
        var firstSign = first.sign();
        var secondSign = second.sign();

        if (firstSign < secondSign) {
            /**
             * 本条件包含：
             * - first 为负、second 为正
             * - first 为负，second 为 0
             * - first 为 0，second 为正
             */
            return true;
        } else if (firstSign > secondSign || firstSign == 0) {
            /**
             * 本条件包含：
             * - first 为正、second 为负
             * - first 为正，second 为 0
             * - first 为 0，second 为负
             * - first、second 同时为 0
             */
            return false;
        } else { // first、second 同号，且都不为 0
            if (first.underlyingIsInt() && second.underlyingIsInt()) { // 如果它们都是小整数
                try {
                    return first.toInt() < second.toInt();
                } catch (OverflowException exception) {
                    Logfx.error("错误：发生了意料之外的异常。", exception);
                    throw new UnexpectedException(exception);
                }
            } else if (first.underlyingIsInt()) { // 如果仅 first 是小整数（如果 first 的绝对值小）
                return firstSign > 0; // 同正时，绝对值小的小
            } else if (second.underlyingIsInt()) { // 如果仅 second 是小整数（如果 second 的绝对值小）
                return firstSign < 0; // 同负时，绝对值大的小
            } else {
                // 仅当它们至少其中一个为大整数时，使用 BigInteger.compareTo 比使用 BigInteger 的减法要略快 10%
                return first.toBigInteger().compareTo(second.toBigInteger()) < 0;
            }
        }
    }

    /**
     * 小于等于：first <= second
     *
     * @since 2022-8-24 v0
     * @lastModified 2022-12-23 v2.0
     */
    public static boolean lessOrEqual(Figure first, Figure second) {
        var firstSign = first.sign();
        var secondSign = second.sign();

        if (firstSign < secondSign || firstSign == 0) {
            /**
             * 本条件包含：
             * - first 为负、second 为正
             * - first 为负，second 为 0
             * - first 为 0，second 为正
             * - first、second 同时为 0
             */
            return true;
        } else if (firstSign > secondSign) {
            /**
             * 本条件包含：
             * - first 为正、second 为负
             * - first 为正，second 为 0
             * - first 为 0，second 为负
             */
            return false;
        } else { // first、second 同号，且都不为 0
            if (first.underlyingIsInt() && second.underlyingIsInt()) { // 如果它们都是小整数
                try {
                    return first.toInt() <= second.toInt();
                } catch (OverflowException exception) {
                    Logfx.error("错误：发生了意料之外的异常。", exception);
                    throw new UnexpectedException(exception);
                }
            } else if (first.underlyingIsInt()) { // 如果仅 first 是小整数（如果 first 的绝对值小）
                return firstSign > 0; // 同正时，绝对值小的小
            } else if (second.underlyingIsInt()) { // 如果仅 second 是小整数（如果 second 的绝对值小）
                return firstSign < 0; // 同负时，绝对值大的小
            } else {
                // 仅当它们至少其中一个为大整数时，使用 BigInteger.compareTo 比使用 BigInteger 的减法要略快 10%
                return first.toBigInteger().compareTo(second.toBigInteger()) <= 0;
            }
        }
    }

    /**
     * 大于：first > second
     *
     * @since 2022-8-24 v0
     * @lastModified 2022-12-23 v2.0
     */
    public static boolean greaterThan(Figure first, Figure second) {
        var firstSign = first.sign();
        var secondSign = second.sign();

        if (firstSign > secondSign) {
            /**
             * 本条件包含：
             * - first 为正、second 为负
             * - first 为正，second 为 0
             * - first 为 0，second 为负
             */
            return true;
        } else if (firstSign < secondSign || firstSign == 0) {
            /**
             * 本条件包含：
             * - first 为负、second 为正
             * - first 为负，second 为 0
             * - first 为 0，second 为正
             * - first、second 同时为 0
             */
            return false;
        } else { // first、second 同号，且都不为 0
            if (first.underlyingIsInt() && second.underlyingIsInt()) { // 如果它们都是小整数
                try {
                    return first.toInt() > second.toInt();
                } catch (OverflowException exception) {
                    Logfx.error("错误：发生了意料之外的异常。", exception);
                    throw new UnexpectedException(exception);
                }
            } else if (first.underlyingIsInt()) { // 如果仅 first 是小整数（如果 first 的绝对值小）
                return firstSign < 0; // 同负时，绝对值小的大
            } else if (second.underlyingIsInt()) { // 如果仅 second 是小整数（如果 second 的绝对值小）
                return firstSign > 0; // 同正时，绝对值大的大
            } else {
                // 仅当它们至少其中一个为大整数时，使用 BigInteger.compareTo 比使用 BigInteger 的减法要略快 10%
                return first.toBigInteger().compareTo(second.toBigInteger()) > 0;
            }
        }
    }

    /**
     * 大于等于：first >= second
     *
     * @since 2022-8-24 v0
     * @lastModified 2022-12-23 v2.0
     */
    public static boolean greaterOrEqual(Figure first, Figure second) {
        var firstSign = first.sign();
        var secondSign = second.sign();

        if (firstSign > secondSign || firstSign == 0) {
            /**
             * 本条件包含：
             * - first 为正，second 为负
             * - first 为正，second 为 0
             * - first 为 0，second 为负
             * - first、second 同时为 0
             */
            return true;
        } else if (firstSign < secondSign) {
            /**
             * 本条件包含：
             * - first 为负，second 为正
             * - first 为负，second 为 0
             * - first 为 0，second 为正
             */
            return false;
        } else { // first、second 同号，且都不为 0
            if (first.underlyingIsInt() && second.underlyingIsInt()) { // 如果它们都是小整数
                try {
                    return first.toInt() >= second.toInt();
                } catch (OverflowException exception) {
                    Logfx.error("错误：发生了意料之外的异常。", exception);
                    throw new UnexpectedException(exception);
                }
            } else if (first.underlyingIsInt()) { // 如果仅 first 是小整数（如果 first 的绝对值小）
                return firstSign < 0; // 同负时，绝对值小的大
            } else if (second.underlyingIsInt()) { // 如果仅 second 是小整数（如果 second 的绝对值小）
                return firstSign > 0; // 同正时，绝对值大的大
            } else {
                // 仅当它们至少其中一个为大整数时，使用 BigInteger.compareTo 比使用 BigInteger 的减法要略快 10%
                return first.toBigInteger().compareTo(second.toBigInteger()) >= 0;
            }
        }
    }

    /**
     * 求两个数中最小的那一个。如果一样大，则返回第一个
     *
     * 注意：这个方法不会返回新对象。因为大多数情况下，调用本方法之后会弃用形参，所以没有必要深克隆
     *
     * @since 2023-1-25
     */
    public static Figure min(Figure first, Figure second) {
        return FigureOperation.lessOrEqual(first, second) ? first : second;
    }

    /**
     * 求两个数中最大的那一个。如果一样大，则返回第一个
     *
     * 注意：这个方法不会返回新对象。因为大多数情况下，调用本方法之后会弃用形参，所以没有必要深克隆
     *
     * @since 2023-1-25
     */
    public static Figure max(Figure first, Figure second) {
        return FigureOperation.greaterOrEqual(first, second) ? first : second;
    }

    /**
     * 整数的乘方。注意：指数 exponent 不能太大
     *
     * @since before 2021-8-5
     */
    public static Figure power(Figure base, Figure exponent) {
        if (base.isZero() && exponent.isZero()) {
            throw new LogicalException("错误：不能计算 0 的 0 次方");
        }
        if (exponent.isNegative()) {
            throw new LogicalException("错误：整数乘法不支持负数次方");
        }

        return new Figure(base.toBigInteger().pow(exponent.toBigInteger().intValue()));
    }

    /**
     * 整数的乘方。注意：指数 exponent 不能太大
     *
     * @since before 2021-8-5
     */
    public static Figure power(long base, long exponent) {
        return FigureOperation.power(new Figure(base), new Figure(exponent));
    }

    /**
     * 求两个数的最大公约数。GCD：Greatest Common Divisor
     *
     * 注意事项：
     * > 当这两个数只有一个为 0 时，结果为另一个数的绝对值。
     * > 特别地，当这两个数均为 0 时，结果为 0。
     * > 其它情况下，结果为正数
     *
     * @since before 2021-8-5
     */
    public static Figure findGcd(Figure first, Figure second) {
        return new Figure(first.toBigInteger().gcd(second.toBigInteger()));
    }

    /**
     * 求两个数的最小公倍数。LCM：Least Common Multiple
     *
     * 算法如下：
     * 先得出这两个数的最大公约数，
     * 然后将其中一个数除以最大公约数，得到其中一个质因子（prime factor）
     * 最后将该质因子另外一个没有除以过公约数的数相乘
     *
     * @since before 2021-8-5
     */
    public static Figure findLcm(Figure first, Figure second) {
        return multiply(first, subtract(second, findGcd(first, second)));
    }

    /**
     * @since 2022-8-29
     */
    public static Figure arrangement(final Figure n, final Figure m) {
        return arrangementForBigResult(n, m);
    }

    /**
     * @since 2022-8-29
     */
    public static Figure arrangement(final int n, final int m) {
        return arrangementForBigResult(new Figure(n), new Figure(m));
    }

    /**
     * 计算排列数，从 n 个数中选 m 个数进行全排列，因此需要 n >= m。
     * 公式：n! / [(n-m)!] = n*(n-1)*...*(n-m+1)
     *
     * @since 2022-8-29
     */
    public static Figure arrangementForBigResult(final Figure n, final Figure m) {
        if (n.isNegative() || n.isZero() || m.isNegative()) { // n > 0，m >= 0
            throw new LogicalException("错误：计算排列数时发现非正数。其中：n = " + n + "，m = " + m);
        }
        if (FigureOperation.lessThan(n, m)) {
            throw new LogicalException("错误：计算排列数时发现大小关系（n < m）有误。其中：n = " + n + "，m = " + m);
        }
        Figure result = Figure.ONE.clone();
        for (Figure i = FigureOperation.subtract(n, m.clone().decreaseOne()); // start = n - (m - 1)
             FigureOperation.lessOrEqual(i, n);
             i.increaseOne()) {
            result = FigureOperation.multiply(result, i);
        }
        return result;
    }

    /**
     * @since 2022-8-29
     */
    public static Figure combination(final Figure n, final Figure m) {
        return combinationForBigResult(n, m);
    }

    /**
     * @since 2022-8-29
     */
    public static Figure combination(final int n, final int m) {
        return combination(new Figure(n), new Figure(m));
    }

    /**
     * 计算组合数，从 n 个数中选 m 个数合成一组，因此需要 n >= m。
     * 公式：A(n,m) / A(m,m) = [n*(n-1)*...*(n-m+1)] / (m!)
     *
     * @since 2022-8-29
     */
    public static Figure combinationForBigResult(final Figure n, final Figure m) {
        if (n.isPositive() && m.isZero()) {
            return Figure.ONE; // 数学规定：C(n,0) = 1
        }
        return FigureOperation.modsQuotient(FigureOperation.arrangementForBigResult(n, m)
                , FigureOperation.factorial(m));
    }

    /**
     * 计算 n!。此方法可以计算运算结果很大的阶乘，但是计算计算速度会慢一些
     *
     * @since 2022-8-29
     */
    public static Figure factorial(final Figure n) {
        return factorialForBigResult(n);
    }

    /**
     * 计算 n!。此方法只能计算运算结果不大的阶乘，计算计算速度会快一些
     *
     * @since 2022-8-29
     */
    public static Figure factorialForSmallResult(final int n) {
        if (n < 0) {
            throw new LogicalException(String.format("错误：不能计算负数 %d 的阶乘", n));
        }
        if (n == 0 || n == 1) {
            return Figure.ONE;
        }
        int result = 1;
        for (int i = 2; i <= n; ++i) {
            result *= i;
        }
        return new Figure(result);
    }

    /**
     * 计算 n!。此方法可以计算运算结果很大的阶乘，但是计算计算速度会慢一些
     *
     * @since 2022-8-29
     */
    public static Figure factorialForBigResult(final Figure n) {
        if (n.isNegative()) {
            throw new LogicalException(String.format("错误：不能计算负数 %d 的阶乘", n));
        }
        if (n.equals(Figure.ZERO) || n.equals(Figure.ONE)) {
            return Figure.ONE; // 数学规定：0!=1
        }
        Figure result = Figure.ONE.clone();
        for (Figure i = Figure.TWO.clone(); FigureOperation.lessOrEqual(i, n); i.increaseOne()) {
            result = FigureOperation.multiply(result, i);
        }
        return result;
    }
}
