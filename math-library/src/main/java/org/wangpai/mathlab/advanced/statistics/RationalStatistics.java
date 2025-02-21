package org.wangpai.mathlab.advanced.statistics;

import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;
import org.wangpai.mathlab.builtin.statistics.primitive.DeviationMode;

/**
 * @since 2024-12-16
 */
public class RationalStatistics {
    /**
     * 求和
     *
     * @since 2022-11-28
     */
    public static Rational sum(Rational... numbers) {
        var result = Rational.ZERO.clone();
        for (var num : numbers) {
            result = RationalOperation.add(result, num);
        }
        return result;
    }

    /**
     * 求最大值
     *
     * @since 2022-11-28
     */
    public static Rational max(Rational... numbers) {
        Rational result = numbers[0];
        for (var num : numbers) {
            if (RationalOperation.lessThan(result, num)) {
                result = num;
            }
        }
        return result;
    }

    /**
     * 求最小值
     *
     * @since 2022-11-28
     */
    public static Rational min(Rational... numbers) {
        Rational result = numbers[0];
        for (var num : numbers) {
            if (RationalOperation.greaterThan(result, num)) {
                result = num;
            }
        }
        return result;
    }

    /**
     * 平均数
     *
     * @since 2022-11-28
     */
    public static Rational average(Rational... numbers) {
        return RationalOperation.divide(
                new Rational(RationalStatistics.sum(numbers)), new Rational(numbers.length));
    }

    /**
     * 方差
     *
     * @since 2022-11-28
     */
    public static Rational variance(Rational[] numbers, DeviationMode mode) {
        Rational temp1 = Rational.ZERO;
        for (var num : numbers) {
            // temp1 += num * num;
            temp1 = RationalOperation.add(temp1, RationalOperation.multiply(num, num));
        }
        var sum = sum(numbers);
        var temp2 = RationalOperation.divide(RationalOperation.multiply(sum, sum), new Rational(numbers.length));
        // result = temp1 - sum * sum / numbers.length;
        var result = RationalOperation.subtract(new Rational(temp1), temp2);

        return switch (mode) {
            case MODE_1 -> RationalOperation.divide(result, new Rational(numbers.length - 1));
            case MODE_2 -> RationalOperation.divide(result, new Rational(numbers.length));
        };
    }

    /**
     * 标准差。本类不支持此方法，此方法禁止调用
     *
     * @since 2022-11-28
     */
    @Deprecated
    public static Rational standDeviation(DeviationMode mode, Rational... numbers) {
        throw new ForbiddenCallingException("错误：本类不支持此方法");
    }
}
