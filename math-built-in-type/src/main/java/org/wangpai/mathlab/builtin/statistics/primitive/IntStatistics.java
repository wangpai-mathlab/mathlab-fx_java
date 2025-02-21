package org.wangpai.mathlab.builtin.statistics.primitive;

import org.wangpai.mathlab.builtin.statistics.distribute.IntDistribution;

/**
 * @since 2022-10-21
 */
public class IntStatistics {
    /**
     * 求和
     *
     * @since 2022-10-21
     */
    public static int sum(int... numbers) {
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
    public static int sum(int[] data, int start, int end) {
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
    public static int absoluteSum(int... numbers) {
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
    public static int quadraticSum(int... numbers) {
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
    public static int max(int... numbers) {
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
    public static int min(int... numbers) {
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
    public static double average(int... numbers) {
        return (double) IntStatistics.sum(numbers) / numbers.length;
    }

    /**
     * 方差
     *
     * 注意：当数据量较大时，此方法有无声溢出的风险
     *
     * @since 2022-10-21
     */
    public static double variance(int[] numbers, DeviationMode mode) {
        var quadraticSumResult = IntStatistics.quadraticSum(numbers);
        var sumResult = IntStatistics.sum(numbers);
        // 注意：当数据量较大（sumResult > 46340）时，此处 sumResult * sumResult 容易无声溢出
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
    public static double standDeviation(int[] numbers, DeviationMode mode) {
        return Math.sqrt(IntStatistics.variance(numbers, mode));
    }

    /**
     * 统计 data 的数据分布
     *
     * @since 2023-2-5
     */
    public static IntDistribution distribute(int[] data, int start, int end) {
        var result = IntDistribution.getInstance(start, end);
        return result.put(data);
    }
}
