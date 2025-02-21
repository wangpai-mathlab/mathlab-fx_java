package org.wangpai.mathlab.builtin.statistics.primitive;

/**
 * @since 2022-10-21
 */
public class DoubleStatistics {
    /**
     * 求和
     *
     * @since 2022-10-21
     */
    public static double sum(double... numbers) {
        var result = 0.0;
        for (var num : numbers) {
            result += num;
        }
        return result;
    }

    /**
     * 将每个元素的绝对值相加求和
     *
     * @since 2022-12-23
     */
    public static double absoluteSum(double... numbers) {
        var result = 0.0;
        for (var num : numbers) {
            result += Math.abs(num);
        }
        return result;
    }

    /**
     * 平方和
     *
     * @since 2022-12-23
     */
    public static double quadraticSum(double... numbers) {
        var result = 0.0;
        for (var num : numbers) {
            result += num * num;
        }
        return result;
    }

    /**
     * 求最大值
     *
     * @since 2022-10-21
     */
    public static double max(double... numbers) {
        var result = numbers[0];
        for (var num : numbers) {
            result = Math.max(result, num);
        }
        return result;
    }

    /**
     * 求最小值
     *
     * @since 2022-10-21
     */
    public static double min(double... numbers) {
        var result = numbers[0];
        for (var num : numbers) {
            result = Math.min(result, num);
        }
        return result;
    }

    /**
     * 平均数
     *
     * @since 2022-10-21
     */
    public static double average(double... numbers) {
        return DoubleStatistics.sum(numbers) / numbers.length;
    }

    /**
     * 方差
     *
     * @since 2022-10-21
     */
    public static double variance(double[] numbers, DeviationMode mode) {
        var quadraticSumResult = DoubleStatistics.quadraticSum(numbers);
        var sumResult = DoubleStatistics.sum(numbers);
        var result = quadraticSumResult - sumResult * sumResult / numbers.length;
        return switch (mode) {
            case MODE_1 -> result / (numbers.length - 1);
            case MODE_2 -> result / numbers.length;
        };
    }

    /**
     * 标准差
     *
     * @since 2022-10-21
     */
    public static double standDeviation(DeviationMode mode, double... numbers) {
        return Math.sqrt(DoubleStatistics.variance(numbers, mode));
    }
}
