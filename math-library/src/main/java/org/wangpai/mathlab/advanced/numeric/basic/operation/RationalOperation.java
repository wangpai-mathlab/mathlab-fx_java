package org.wangpai.mathlab.advanced.numeric.basic.operation;

import java.util.Random;
import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;

/**
 * @since 2021-8-1
 */
public final class RationalOperation {
    private static final Random random = new Random();
    private static final long randomOffset = ((long) Integer.MAX_VALUE) + 1; // 2 ^ 31
    private static final long randomDenominator = ((long) Integer.MAX_VALUE) * 2 + 1; // (2 ^ 32) - 1

    /*--------------- 加减乘除基本运算 ---------------*/

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 算法如下：
     * 先得出两个分母的最大公约数，然后将这两个分母分别除以最大公约数，得到两个质因子（prime factor）
     * 最终的分子为：第一个分子乘以第二个分母的质因子，第二个分子乘以第一个分母的质因子，然后把得到的结果相加
     * 最终的分母为：其中一个分母乘以另一个分母的质因子（也即这两个分母的最小公倍数）
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational add(Rational first, Rational second) {
        final var firstNumerator = first.getNumeratorUnsafely();
        final var firstDenominator = first.getDenominatorUnsafely();
        final var secondNumerator = second.getNumeratorUnsafely();
        final var secondDenominator = second.getDenominatorUnsafely();

        Figure gcd = FigureOperation.findGcd(firstDenominator, secondDenominator);

        Figure firstPrimeFactor = FigureOperation.modsQuotient(firstDenominator, gcd);
        Figure secondPrimeFactor = FigureOperation.modsQuotient(secondDenominator, gcd);

        return new Rational(
                FigureOperation.add(
                        FigureOperation.multiply(firstNumerator, secondPrimeFactor),
                        FigureOperation.multiply(secondNumerator, firstPrimeFactor)),
                FigureOperation.multiply(firstDenominator, secondPrimeFactor));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 算法：第一个数加上第二个数的相反数
     *
     * @since before 2021-8-5
     */
    public static Rational subtract(Rational first, Rational second) {
        return RationalOperation.add(first, getOpposite(second));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 算法：两个数的分子、分母分别相乘
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational multiply(Rational first, Rational second) {
        return new Rational(
                FigureOperation.multiply(first.getNumeratorUnsafely(), second.getNumeratorUnsafely()),
                FigureOperation.multiply(first.getDenominatorUnsafely(), second.getDenominatorUnsafely()));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 算法：将第二个整数与第一个有理数的分子相乘
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational multiply(Rational first, Figure second) {
        return new Rational(
                FigureOperation.multiply(first.getNumeratorUnsafely(), second),
                first.getDenominatorUnsafely());
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since before 2021-8-5
     */
    public static Rational multiply(Rational first, long second) {
        return RationalOperation.multiply(first, new Figure(second));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 算法：两个整数直接相乘
     *
     * @since before 2021-8-5
     */
    public static Rational multiply(Figure first, Figure second) {
        return new Rational(FigureOperation.multiply(first, second));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 算法：交叉相乘
     *
     * @since 2022-12-11 v1
     *        2021-8-5 v0
     */
    public static Rational divide(Rational dividend, Rational divisor) {
        if (divisor.isZero()) {
            throw new LogicalException("错误：0 不能作除数");
        }
        return new Rational(
                FigureOperation.multiply(dividend.getNumeratorUnsafely(), divisor.getDenominatorUnsafely()),
                FigureOperation.multiply(dividend.getDenominatorUnsafely(), divisor.getNumeratorUnsafely()));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * 算法：将第二个整数与第一个有理数的分母相乘
     *
     * @since 2022-12-11
     */
    public static Rational divide(Rational dividend, Figure divisor) {
        return new Rational(
                dividend.getNumeratorUnsafely(),
                FigureOperation.multiply(dividend.getDenominatorUnsafely(), divisor));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2022-12-11
     */
    public static Rational divide(Rational dividend, long divisor) {
        return RationalOperation.divide(dividend, new Figure(divisor));
    }

    /**
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since before 2021-8-5
     */
    public static Rational divide(Figure dividend, Figure divisor) {
        if (divisor.isZero()) {
            throw new LogicalException("错误：0 不能作除数");
        }
        return RationalOperation.multiply(new Rational(dividend),
                RationalOperation.getReciprocal(new Rational(divisor)));
    }

    /**************** 加减乘除基本运算 ****************/

    /**
     * 求相反数
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational getOpposite(Rational rational) {
        return new Rational(
                FigureOperation.getOpposite(rational.getNumeratorUnsafely()),
                rational.getDenominatorUnsafely().clone());
    }

    /**
     * 求绝对值
     *
     * @since 2022-8-23
     */
    public static Rational getAbsolute(Rational rational) {
        return new Rational(FigureOperation.getAbsolute(rational.getNumeratorUnsafely()),
                FigureOperation.getAbsolute(rational.getDenominatorUnsafely()));
    }

    /**
     * 求倒数
     *
     * @since before 2021-8-5
     */
    public static Rational getReciprocal(Rational rational) {
        if (rational.isZero()) {
            throw new LogicalException("错误：0 没有倒数");
        }
        return new Rational(
                rational.getDenominatorUnsafely().clone(),
                rational.getNumeratorUnsafely().clone());
    }

    /**
     * 小于等于：first <= second
     *
     * @since 2022-8-24
     */
    public static boolean lessOrEqual(Rational first, Rational second) {
        var subtractResult = RationalOperation.subtract(first, second);
        return subtractResult.isNegative() || subtractResult.isZero();
    }

    /**
     * 小于：first < second
     *
     * @since 2022-8-29
     */
    public static boolean lessThan(Rational first, Rational second) {
        return RationalOperation.subtract(first, second).isNegative();
    }

    /**
     * 大于等于：first >= second
     *
     * @since 2022-8-24
     */
    public static boolean greaterOrEqual(Rational first, Rational second) {
        var subtractResult = RationalOperation.subtract(first, second);
        return subtractResult.isPositive() || subtractResult.isZero();
    }

    /**
     * 大于：first > second
     *
     * @since 2022-8-24
     */
    public static boolean greaterThan(Rational first, Rational second) {
        return RationalOperation.subtract(first, second).isPositive();
    }

    /**
     * 求两个数中最小的那一个。如果一样大，则返回第一个
     *
     * 注意：这个方法不会返回新对象。因为大多数情况下，调用本方法之后会弃用形参，所以没有必要深克隆
     *
     * @since 2023-1-25
     */
    public static Rational min(Rational first, Rational second) {
        return RationalOperation.lessOrEqual(first, second) ? first : second;
    }

    /**
     * 求两个数中最大的那一个。如果一样大，则返回第一个
     *
     * 注意：这个方法不会返回新对象。因为大多数情况下，调用本方法之后会弃用形参，所以没有必要深克隆
     *
     * @since 2023-1-25
     */
    public static Rational max(Rational first, Rational second) {
        return RationalOperation.greaterOrEqual(first, second) ? first : second;
    }

    /**
     * 有理数乘方。此方法禁止使用
     *
     * 占位空方法
     *
     * @since before 2021-8-5
     */
    @Deprecated
    public static Rational power(Rational base, Rational exponent) {
        throw new ForbiddenCallingException("错误：不支持指数为有理数的乘方"); // 原因是，当指数为有理数时，其结果为实数，不一定为有理数
    }

    /**
     * 有理数的整数乘方。注意：指数 exponent 不能太大
     *
     * 算法：将分子、分母分别进行整数乘方运算
     *
     * @since 2022-8-23
     */
    public static Rational power(Rational base, Figure exponent) {
        if (base.isZero() && exponent.isZero()) {
            throw new LogicalException("错误：不能计算 0 的 0 次方");
        }
        if (base.isZero() && exponent.isNegative()) {
            throw new LogicalException("错误：不能计算 0 的负数次方");
        }
        if (exponent.isZero()) {
            return new Rational(1);
        }

        boolean needReciprocal = exponent.isNegative();
        Figure multiplyTimes = FigureOperation.getAbsolute(exponent);
        Rational result = new Rational(
                FigureOperation.power(base.getNumeratorUnsafely(), multiplyTimes),
                FigureOperation.power(base.getDenominatorUnsafely(), multiplyTimes));

        if (needReciprocal) {
            return getReciprocal(result);
        } else {
            return result;
        }
    }

    /**
     * 向上取整。得到一个不小于 rational 的最小整数。如 对 1.5 的向上取整结果为 2
     *
     * @since 2022-8-24
     */
    public static Figure roundUp(Rational rational) {
        if (rational.getDenominatorUnsafely().equals(Figure.ONE)) {
            return rational.getNumeratorUnsafely().clone();
        } else {
            var quotient = FigureOperation.modsQuotient(rational.getNumeratorUnsafely(),
                    rational.getDenominatorUnsafely());
            return FigureOperation.add(quotient, Figure.ONE);
        }
    }

    /**
     * 向下取整。得到一个不大于 rational 的最大整数。如 对 1.5 的向上取整结果为 1
     *
     * @since 2022-8-24
     */
    public static Figure roundDown(Rational rational) {
        return FigureOperation.modsQuotient(rational.getNumeratorUnsafely(), rational.getDenominatorUnsafely());
    }

    /**
     * 得到 0 至 1 的分数随机数
     *
     * 此方法运行 10 万次的总耗时约为 9s。与上个版本相比，本版本的用时约只有它的 30%。
     * 运行 100 万次的总耗时约为 19s。与上个版本相比，本版本的用时约只有它的 8%。
     * 此比例如此不对称，说明运行次数很少时，其它操作所占时间占比过高。因此本方法随运行次数增大时，效率的优势会更明显
     *
     * @since 2022-8-24 v0
     *        2022-9-10 v1.0
     */
    public static Rational random0To1() {
        // random.nextInt() 的范围为 [- 2 ^ 31, (2 ^ 31) - 1]
        long randomInt = random.nextInt() + randomOffset; // 得到一个非负随机数，范围为 [0, (2 ^ 32) - 1]
        return new Rational(randomInt, randomDenominator);
    }

    /**
     * 得到范围为 [0, maxRange] 的分数随机数
     *
     * @since 2022-9-10
     */
    public static Rational random(int maxRange) {
        // random.nextInt() 的范围为 [- 2 ^ 31, (2 ^ 31) - 1]
        long randomInt = random.nextInt() + randomOffset; // 得到一个非负随机数，范围为 [0, (2 ^ 32) - 1]
        return new Rational(randomInt * maxRange, randomDenominator);
    }
}
