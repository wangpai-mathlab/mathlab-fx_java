package org.wangpai.mathlab.advanced.numeric.basic.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Decimal;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.exception.unchecked.LogicalException;

/**
 * 本类记录了 RationalOperation 的历史方法以供对比测试，
 * 只有未来可能需要进行对比测试时才需要添加到本类中。
 *
 * 本类只短暂记录存在 bug 的代码
 *
 * 本类禁止在非测试类中调用
 *
 * @since 2022-12-11
 */
public class RationalOperationHistory {
    /**
     * 算法：第一个数乘以第二个数的倒数
     *
     * @since 2021-8-5 v0
     */
    public static Rational divide_v0(Rational first, Rational second) {
        if (second.isZero()) {
            throw new LogicalException("错误：0 不能作除数");
        }
        return RationalOperation.multiply(first, RationalOperation.getReciprocal(second));
    }

    /**
     * RationalOperation.random0To1() 的 v1.0 版本
     *
     * 得到 0 至 1 的随机数
     *
     * @param accuracy ：它代表产生小数点后保留多少位的小数。这种保留是一种四舍五入。
     *                 如当 accuracy 为 1 时，只会产生如 0.1、0.2 这样的随机数
     * @since 2022-8-24
     */
    public static Rational random0To1_v0(int accuracy) {
        // Math.random() 会得到 0 至 1 的浮点数
        var doubleString = String.format("%." + accuracy + "f", Math.random());
        return new Decimal(doubleString).toRational();
    }

    @Test
    void random0To1_v0() {
        final long startTime = System.currentTimeMillis();
        int maxTimes = 100000;
        Rational sum = Rational.ZERO.clone();
        int accuracy = 10;
        for (int time = 0; time < maxTimes; ++time) {
            Rational random0To1 = random0To1_v0(accuracy);
            sum = RationalOperation.add(sum, random0To1);
//            System.out.println(random0To1.toDouble());
        }
//        System.out.println("-----------");
        System.out.println(RationalOperation.divide(sum, new Rational(maxTimes)).toDouble());
        System.out.printf("******** 应用后台程序退出：%fs ********%n",
                (System.currentTimeMillis() - startTime) / 1000.0);
    }

    @Test
    void random0To1_v1_compare_v0() {
        int maxTimes = 1000000;
        long newInterval;
        long oldInterval;
        {
            final long startTimeNew = System.currentTimeMillis();
            for (int time = 0; time < maxTimes; ++time) {
                RationalOperation.random0To1();
            }
            newInterval = System.currentTimeMillis() - startTimeNew;
            System.out.printf("******** 应用后台程序退出：%fs ********%n", newInterval / 1000.0);
        }

        {
            final long startTimeOld = System.currentTimeMillis();
            for (int time = 0; time < maxTimes; ++time) {
                random0To1_v0(10);
            }
            oldInterval = System.currentTimeMillis() - startTimeOld;
            System.out.printf("******** 应用后台程序退出：%fs ********%n", (oldInterval) / 1000.0);
        }
        System.out.println(oldInterval * 1.0 / newInterval);
    }
}
