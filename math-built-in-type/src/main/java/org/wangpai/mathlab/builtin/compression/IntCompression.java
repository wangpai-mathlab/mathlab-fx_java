package org.wangpai.mathlab.builtin.compression;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.wangpai.mathlab.builtin.interval.DoubleInterval;
import org.wangpai.mathlab.builtin.statistics.primitive.IntStatistics;

/**
 * @since 2022-12-3
 */
public class IntCompression {
    /**
     * 如果是默认模式，则将 data 中的每个元素变成如下值：
     * - 如果比 reference 大，变成 1。
     * - 如果比 reference 小，变成 -1。
     * - 如果与 reference 相等，变成 0。
     *
     * @since 2022-12-3
     */
    public static int[] halfPartition(int[] data, int reference, HalfPartitionMode mode) {
        var result = new int[data.length];
        switch (mode) {
            case DEFAULT -> {
                for (int index = 0; index < result.length; ++index) {
                    if (data[index] > reference) {
                        result[index] = 1;
                    } else if (data[index] < reference) {
                        result[index] = -1;
                    } else {
                        result[index] = 0;
                    }
                }
            }
            case UP -> {
                for (int index = 0; index < result.length; ++index) {
                    if (data[index] >= reference) {
                        result[index] = 1;
                    } else {
                        result[index] = -1;
                    }
                }
            }
            case DOWN -> {
                for (int index = 0; index < result.length; ++index) {
                    if (data[index] <= reference) {
                        result[index] = -1;
                    } else {
                        result[index] = 1;
                    }
                }
            }
        }

        return result;
    }

    /**
     * 此方法会改变形参。此方法的返回值将按区间起点排序
     *
     * @since 2023-1-9
     * @return <区间，此区间包含的点的个数>
     */
    public static Map<DoubleInterval, Integer> partition(int[] data, int intervalNum) {
        Arrays.sort(data);
        int min = data[0];
        int max = data[data.length - 1];
        // 视 data 的数据位于区间 [min, max + 0.001)
        double totalLength = max - min + 0.001;
        double intervalPerLength = totalLength / intervalNum;
        var orderedMap = new LinkedHashMap<DoubleInterval, Integer>(intervalNum);
        int pointer = 0;
        for (int index = 0; index < intervalNum; index++) {
            var interval = new DoubleInterval(min + intervalPerLength * index, intervalPerLength);
            int count = 0;
            while (pointer < data.length) {
                if (interval.contain(data[pointer])) {
                    ++count;
                    ++pointer;
                } else {
                    break;
                }
            }
            orderedMap.put(interval, count);
        }
        return orderedMap;
    }

    /**
     * 将 data 进行均匀压缩
     *
     * 为了提高效率，本方法不会对形参进行检查，所以要求形参：
     * - data 每个元素的都在 [formerRange[0], formerRange[1]] 之中
     * - formerRange、compressedRange 各为长度为 2 的数组，且各自的第一个元素不大于第二个元素
     *
     * @param data 储存需要压缩的原数据
     * @param formerRange 原数据的取值范围：[formerRange[0], formerRange[1]]
     * @param compressedRange 数据压缩之后的目标取值范围：[compressedRange[0], compressedRange[1]]
     * @since 2023-2-4
     */
    public static double[] uniformlyWnc(int[] data, int[] formerRange, double[] compressedRange) {
        /**
         * 设数据为 x，范围为 [a, b]。转换之后的数据为 fx，范围为 [c, d]。
         * dx = b - a，dfx = d - c，k = dfx / dx。
         * 转换公式为：fx = (x-a) / dx * dfx + c。
         * 变形之后为：fx = x * k - a * k + c
         */
        int a = formerRange[0];
        int b = formerRange[1];
        int dx = b - a;
        double c = compressedRange[0];
        double d = compressedRange[1];
        double dfx = d - c;
        double k = dfx / dx;
        double ak = k * a;
        double m = c - ak;

        var result = new double[data.length];
        for (int index = 0; index < data.length; ++index) {
            result[index] = k * data[index] + m;
        }

        return result;
    }

    /**
     * 将 data 中的元素每 num 个进行合并，尾部剩下的元素丢弃。
     * 因此，如果 data 有 n 个元素，则将返回 n / num 个元素
     *
     * 此方法不会改变形参
     *
     * @since 2023-2-1
     */
    public static long[] combine(int[] data, int num) {
        var resultNum = data.length / num;
        // 即便是 Integer.MAX_VALUE 个值为 Integer.MAX_VALUE 的数相加，其求和结果也不会超过 long 的范围
        var result = new long[resultNum];
        for (int index = 0; index < result.length; ++index) {
            result[index] = IntStatistics.sum(data, index * num, (index + 1) * num);
        }
        return result;
    }
}
