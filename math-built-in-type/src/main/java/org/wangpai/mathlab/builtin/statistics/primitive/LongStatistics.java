package org.wangpai.mathlab.builtin.statistics.primitive;

/**
 * @since 2022-10-21
 */
public class LongStatistics {
    /**
     * 求和
     *
     * @since 2022-10-21
     */
    public static long sum(long... numbers) {
        var result = 0;
        for (var num : numbers) {
            result += num;
        }
        return result;
    }

    /**
     * 求和
     *
     * @since 2022-10-21
     */
    public static long sum(int... numbers) {
        var result = 0;
        for (var num : numbers) {
            result += num;
        }
        return result;
    }

    /**
     * 对 data 中下标为 [start, end) 的元素进行求和
     *
     * 为了提高效率，本方法不会对形参进行检查，请自行保证
     *
     * @since 2022-10-21
     */
    public static long sum(long[] data, int start, int end) {
        var result = 0;
        for (int index = start; index < end; ++index) {
            result += data[index];
        }
        return result;
    }

    /**
     * 对 data 中下标为 [start, end) 的元素进行求和
     *
     * 为了提高效率，本方法不会对形参进行检查，请自行保证
     *
     * @since 2022-10-21
     */
    public static long sum(int[] data, int start, int end) {
        var result = 0;
        for (int index = start; index < end; ++index) {
            result += data[index];
        }
        return result;
    }

    /**
     * 将每个元素的绝对值相加求和
     *
     * @since 2022-12-23
     */
    public static long absoluteSum(long... numbers) {
        var result = 0;
        for (var num : numbers) {
            result += Math.abs(num);
        }
        return result;
    }

    /**
     * 将每个元素的绝对值相加求和
     *
     * @since 2022-12-23
     */
    public static long absoluteSum(int... numbers) {
        var result = 0;
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
    public static long quadraticSum(long... numbers) {
        var result = 0;
        for (var num : numbers) {
            result += num * num;
        }
        return result;
    }

    /**
     * 平方和
     *
     * @since 2022-12-23
     */
    public static long quadraticSum(int... numbers) {
        var result = 0;
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
    public static long max(long... numbers) {
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
    public static long min(long... numbers) {
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
    public static double average(long... numbers) {
        return (double) LongStatistics.sum(numbers) / numbers.length;
    }

    /**
     * 平均数
     *
     * @since 2022-10-21
     */
    public static double average(int... numbers) {
        return (double) LongStatistics.sum(numbers) / numbers.length;
    }

    /**
     * 方差
     *
     * @since 2022-10-21
     */
    public static double variance(long[] numbers, DeviationMode mode) {
        var quadraticSumResult = LongStatistics.quadraticSum(numbers);
        var sumResult = LongStatistics.sum(numbers);
        var result = quadraticSumResult - sumResult * sumResult / (double) numbers.length;
        return switch (mode) {
            case MODE_1 -> result / (numbers.length - 1);
            case MODE_2 -> result / numbers.length;
        };
    }

    /**
     * 方差
     *
     * @since 2022-10-21
     */
    public static double variance(int[] numbers, DeviationMode mode) {
        var quadraticSumResult = LongStatistics.quadraticSum(numbers);
        var sumResult = LongStatistics.sum(numbers);
        var result = quadraticSumResult - sumResult * sumResult / (double) numbers.length;
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
    public static double standDeviation(long[] numbers, DeviationMode mode) {
        return Math.sqrt(LongStatistics.variance(numbers, mode));
    }

    /**
     * 标准差
     *
     * @since 2022-10-21
     */
    public static double standDeviation(int[] numbers, DeviationMode mode) {
        return Math.sqrt(LongStatistics.variance(numbers, mode));
    }
}
