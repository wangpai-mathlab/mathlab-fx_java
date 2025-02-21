package org.wangpai.mathlab.advanced.statistics;

import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;
import org.wangpai.mathlab.builtin.statistics.primitive.DeviationMode;
import org.wangpai.exception.unchecked.ForbiddenCallingException;

/**
 * @since 2022-11-28
 */
public class FigureStatistics {
    /**
     * 求和
     *
     * @since 2022-11-28
     */
    public static Figure sum(Figure... numbers) {
        var result = Figure.ZERO.clone();
        for (var num : numbers) {
            result = FigureOperation.add(result, num);
        }
        return result;
    }

    /**
     * 求和
     *
     * @since 2023-2-5
     */
    public static Figure sum(int... numbers) {
        var result = Figure.ZERO.clone();
        for (var num : numbers) {
            result = FigureOperation.add(result, new Figure(num));
        }
        return result;
    }

    /**
     * 求最大值
     *
     * @since 2022-11-28
     */
    public static Figure max(Figure... numbers) {
        Figure result = numbers[0];
        for (var num : numbers) {
            if (FigureOperation.lessThan(result, num)) {
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
    public static Figure min(Figure... numbers) {
        Figure result = numbers[0];
        for (var num : numbers) {
            if (FigureOperation.greaterThan(result, num)) {
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
    public static Rational average(Figure... numbers) {
        return RationalOperation.divide(
                new Rational(FigureStatistics.sum(numbers)), new Rational(numbers.length));
    }

    /**
     * 平均数
     *
     * @since 2023-2-5
     */
    public static Rational average(int... numbers) {
        return RationalOperation.divide(
                new Rational(FigureStatistics.sum(numbers)), new Rational(numbers.length));
    }

    /**
     * 方差
     *
     * @since 2022-11-28
     */
    public static Rational variance(Figure[] numbers, DeviationMode mode) {
        Figure temp1 = Figure.ZERO;
        for (var num : numbers) {
            // temp1 += num * num;
            temp1 = FigureOperation.add(temp1, FigureOperation.multiply(num, num));
        }
        var sum = sum(numbers);
        var temp2 = RationalOperation.divide(FigureOperation.multiply(sum, sum), new Figure(numbers.length));
        // result = temp1 - sum * sum / numbers.length;
        var result = RationalOperation.subtract(new Rational(temp1), temp2);

        return switch (mode) {
            case MODE_1 -> RationalOperation.divide(result, new Rational(numbers.length - 1));
            case MODE_2 -> RationalOperation.divide(result, new Rational(numbers.length));
        };
    }

    /**
     * 方差
     *
     * @since 2023-2-5
     */
    public static Rational variance(int[] numbers, DeviationMode mode) {
        Figure temp1 = Figure.ZERO;
        for (var num : numbers) {
            // temp1 += num * num;
            temp1 = FigureOperation.add(temp1, FigureOperation.multiply(num, num));
        }
        var sum = sum(numbers);
        var temp2 = RationalOperation.divide(FigureOperation.multiply(sum, sum), new Figure(numbers.length));
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
    public static Rational standDeviation(DeviationMode mode, Figure... numbers) {
        throw new ForbiddenCallingException("错误：本类不支持此方法");
    }
}
