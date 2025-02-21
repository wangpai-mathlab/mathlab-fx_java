package org.wangpai.mathlab.advanced.numeric.basic.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.exception.checked.failed.OverflowException;
import org.wangpai.exception.unchecked.UnexpectedException;
import org.wangpai.logfx.Logfx;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 本类记录了 FigureOperation 的历史方法以供对比测试，
 * 只有未来可能需要进行对比测试时才需要添加到本类中。
 *
 * 本类只短暂记录存在 bug 的代码
 *
 * 本类禁止在非测试类中调用
 *
 * @since 2022-11-19
 */
public class FigureOperationHistory {
    /**
     *
     * 求两个数的最大公约数。GCD：Greatest Common Divisor
     *
     * 注意事项：
     * > 当这两个数只有一个为 0 时，结果为另一个数的绝对值。
     * > 特别地，当这两个数均为 0 时，结果为 0。
     * > 其它情况下，结果为正数
     *
     * @since 2021-7-30 v0
     */
    public static Figure findGcd_v_0(Figure first, Figure second) {
        if (first.isZero()) {
            return second;
        }
        if (second.isZero()) {
            return first;
        }

        var firstClone = first.clone();
        var secondClone = second.clone();

        if (firstClone.isNegative()) {
            firstClone = FigureOperation.getOpposite(firstClone);
        }
        if (secondClone.isNegative()) {
            secondClone = FigureOperation.getOpposite(secondClone);
        }

        while (!secondClone.isZero()) {
            var middleResult = FigureOperation.mod(firstClone, secondClone);
            firstClone = secondClone;
            secondClone = middleResult;
        }

        return firstClone;
    }

    /**
     * 求两个数的最小公倍数。LCM：Least Common Multiple
     *
     * 算法如下：先得出这两个数的最大公约数，
     * 然后将其中一个数除以最大公约数，得到其中一个质因子（prime factor）
     * 最后将该质因子另外一个没有除以过公约数的数相乘
     *
     * @since 2021-7-30 v0
     */
    public static Figure findLcm_v_0(Figure first, Figure second) {
        return FigureOperation.multiply(first,
                FigureOperation.subtract(
                        second, findGcd_v_0(first, second)));
    }

    /**
     * FigureOperation.lessThan 的 v2.0 版本。此版本修复了 v1.2 版本的 bug
     *
     * @since 2022-12-23 v2.0
     */
    public static boolean lessThan_v2_0(Figure first, Figure second) {
        var firstSign = first.sign();
        var secondSign = second.sign();

        if (firstSign >= 0 && secondSign <= 0) {
            /**
             * first 非负，second 非正。本条件包含：
             * - first 为正、second 为负
             *
             * - first、second 同时为 0
             * - first 为正，second 为 0
             * - first 为 0，second 为负
             */
            return false;
        } else if (firstSign <= 0 && secondSign >= 0) { // first 非正，second 非负（本条件包含 first、second 同时为 0）
            /**
             * first 非正，second 非负。本条件包含：
             * - first 为负、second 为正
             *
             * - first 为负，second 为 0
             * - first 为 0，second 为正
             */
            return true;
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
     * FigureOperation.lessThan 的 v2.1 版本
     *
     * @since 2022-12-23 v2.1
     */
    public static boolean lessThan_v2_1(Figure first, Figure second) {
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
     * FigureOperation.lessOrEqual 的 v0 版本
     *
     * @since 2022-8-24 v0
     */
    public static boolean lessOrEqual_v0(Figure first, Figure second) {
        var subtractResult = FigureOperation.subtract(first, second);
        return subtractResult.isNegative() || subtractResult.isZero();
    }

    /**
     * FigureOperation.lessOrEqual 的 v1 版本
     *
     * 1 亿次测试结果表明：
     * 当两个数均为 int 类型时，
     * 对于 v0 版本，不管两个数相差如何，运行时间总约为 0.006 秒。
     * 对于 v1 版本，当两个数的绝对值相差很远时，v1 版本的运行时约 2 ~ 10 秒。且相差越远，运行时间越长。
     * 当相差很近时，v1 版本的运行时约 0.5 秒。
     * 可见，v0 版本比 v1 版本快了 100 ~ 2000 倍。
     * 当两个数均为 long 类型时，不管两个数相差如何，v0、v1 运行时间总分别约为 40 ~ 45 秒。且 v1 比 v0 稍快 3 秒左右
     * 综上，也就是说，当数很小时，使用 BigInteger 的减法来实现大小比较反而要比使用 BigInteger.compareTo 方法要快
     *
     * @since 2022-11-18 v1
     */
    public static boolean lessOrEqual_v1(Figure first, Figure second) {
        return first.toBigInteger().compareTo(second.toBigInteger()) <= 0;
    }

    /**
     * FigureOperation.lessOrEqual 的 v1.1 版本
     *
     * 1 亿次测试结果表明：
     * 当两个数均为 int 类型时，
     * 对于 v0 版本，不管两个数相差如何，运行时间总约为 0.006 秒。
     * 对于 v1.1 版本，不管两个数相差如何，运行时间总约为 0.004 秒。
     * 当两个数均为 long 类型时，不管两个数相差如何，v0、v1.1 运行时间总分别约为 40 ~ 45 秒。且 v1.1 比 v0 稍快 3 秒左右
     * 综上可见，v1.1 版本比 v0 版本略快。
     *
     * @since 2022-11-19 v1.1
     */
    public static boolean lessOrEqual_v1_1(Figure first, Figure second) {
        if (first.underlyingIsInt() && second.underlyingIsInt()) {
            try {
                return first.toInt() <= second.toInt();
            } catch (OverflowException exception) {
                Logfx.error("错误：发生了意料之外的异常。", exception);
                throw new UnexpectedException(exception);
            }
        } else {
            return first.toBigInteger().compareTo(second.toBigInteger()) <= 0;
        }
    }

    @Test
    public void lessOrEqual_v0() {
        long pv = 2344235543L; // pv：positive Value
        assertFalse(lessOrEqual_v0(new Figure(1), new Figure(-1)));
        assertTrue(lessOrEqual_v0(new Figure(-1), new Figure(1)));
        assertTrue(lessOrEqual_v0(new Figure(1), new Figure(1)));
        assertTrue(lessOrEqual_v0(new Figure(-1), new Figure(-1)));

        assertFalse(lessOrEqual_v0(new Figure(pv), new Figure(-pv)));
        assertTrue(lessOrEqual_v0(new Figure(-pv), new Figure(pv)));
        assertTrue(lessOrEqual_v0(new Figure(pv), new Figure(pv)));
        assertTrue(lessOrEqual_v0(new Figure(-pv), new Figure(-pv)));

        assertTrue(lessOrEqual_v0(new Figure(1), new Figure(pv)));
        assertFalse(lessOrEqual_v0(new Figure(pv), new Figure(1)));
    }

    @Test
    public void lessOrEqual_v1() {
        long pv = 2344235543L; // pv：positive Value
        assertFalse(lessOrEqual_v1(new Figure(1), new Figure(-1)));
        assertTrue(lessOrEqual_v1(new Figure(-1), new Figure(1)));
        assertTrue(lessOrEqual_v1(new Figure(1), new Figure(1)));
        assertTrue(lessOrEqual_v1(new Figure(-1), new Figure(-1)));

        assertFalse(lessOrEqual_v1(new Figure(pv), new Figure(-pv)));
        assertTrue(lessOrEqual_v1(new Figure(-pv), new Figure(pv)));
        assertTrue(lessOrEqual_v1(new Figure(pv), new Figure(pv)));
        assertTrue(lessOrEqual_v1(new Figure(-pv), new Figure(-pv)));

        assertTrue(lessOrEqual_v1(new Figure(1), new Figure(pv)));
        assertFalse(lessOrEqual_v1(new Figure(pv), new Figure(1)));
    }

    @Test
    public void lessOrEqual_v1_1() {
        long pv = 2344235543L; // pv：positive Value
        assertFalse(lessOrEqual_v1_1(new Figure(1), new Figure(-1)));
        assertTrue(lessOrEqual_v1_1(new Figure(-1), new Figure(1)));
        assertTrue(lessOrEqual_v1_1(new Figure(1), new Figure(1)));
        assertTrue(lessOrEqual_v1_1(new Figure(-1), new Figure(-1)));

        assertFalse(lessOrEqual_v1_1(new Figure(pv), new Figure(-pv)));
        assertTrue(lessOrEqual_v1_1(new Figure(-pv), new Figure(pv)));
        assertTrue(lessOrEqual_v1_1(new Figure(pv), new Figure(pv)));
        assertTrue(lessOrEqual_v1_1(new Figure(-pv), new Figure(-pv)));

        assertTrue(lessOrEqual_v1_1(new Figure(1), new Figure(pv)));
        assertFalse(lessOrEqual_v1_1(new Figure(pv), new Figure(1)));
    }

    public void lessOrEqual_Speed0() {
        int maxTimes = 100000000;
        Figure first = new Figure(34435434357432L);
        Figure second = new Figure(344356423432L);

        {
            final long startTime = System.currentTimeMillis();
            for (int time = 0; time < maxTimes; ++time) {
                lessOrEqual_v0(first, second);
            }
            System.out.printf("******** 代码块 lessOrEqual_v0 运行时间：%fs ********%n",
                    (System.currentTimeMillis() - startTime) / 1000.0);
        }
        {
            final long startTime = System.currentTimeMillis();
            for (int time = 0; time < maxTimes; ++time) {
                lessOrEqual_v1(first, second);
            }
            System.out.printf("******** 代码块 lessOrEqual_v1 运行时间：%fs ********%n",
                    (System.currentTimeMillis() - startTime) / 1000.0);
        }
        {
            final long startTime = System.currentTimeMillis();
            for (int time = 0; time < maxTimes; ++time) {
                lessOrEqual_v1_1(first, second);
            }
            System.out.printf("******** 代码块 lessOrEqual_v1_1 运行时间：%fs ********%n",
                    (System.currentTimeMillis() - startTime) / 1000.0);
        }
    }

    @Test
    public void lessOrEqual_Speed() {
        // 预运行
        this.lessOrEqual_Speed0();
        this.lessOrEqual_Speed0();
    }
}
