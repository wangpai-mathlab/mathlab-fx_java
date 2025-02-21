package org.wangpai.mathlab.builtin.interval;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 本类描述的是一种左闭右开的区间：[start, start + length)
 *
 * @since 2023-1-9
 */
@Getter
@Accessors(chain = true)
public class DoubleInterval implements Comparable<DoubleInterval> {
    private double start; // 区间起点

    private double length; // 每个区间的长度（除最后一个区间外）

    /**
     * @since 2023-2-11
     */
    public DoubleInterval(double start, double length) {
        this.start = start;
        this.length = length;
    }

    /**
     * @since 2023-2-11
     */
    public static DoubleInterval getInstance(double start, double length) {
        return new DoubleInterval(start, length);
    }

    /**
     * 得到一个左闭右开的区间 [start, end)
     *
     * @since 2023-1-10
     */
    public static DoubleInterval getInstanceByEndpoint(double start, double end) {
        return new DoubleInterval(start, end - start);
    }

    /**
     * @since 2023-2-12
     */
    public double getEnd() {
        return this.start + this.length;
    }

    /**
     * 判断 value 值是否在本区间中
     *
     * @since 2022-11-21
     */
    public boolean contain(long value) {
        return value >= this.start && value < this.start + this.length;
    }

    /**
     * 判断 value 值是否在这一系列区间中
     *
     * @since 2022-11-21
     */
    public static boolean contain(long value, DoubleInterval[] intervals) {
        for (var interval : intervals) {
            if (interval.contain(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 得到本区间的中心
     *
     * @since 2022-11-22
     */
    public double intervalCenter() {
        return this.start + (this.length / 2);
    }

    /**
     * @since 2022-12-24
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof DoubleInterval interval) {
            return this.start == interval.start && this.length == interval.length;
        }

        return false;
    }

    /**
     * @since 2023-1-11
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.length);
    }

    /**
     * 统计区间的长度之和
     *
     * @since 2022-12-25
     */
    public static long accumulate(DoubleInterval[] intervals) {
        long sum = 0L;
        for (var interval : intervals) {
            sum += interval.length;
        }
        return sum;
    }

    /**
     * 将区间排序。排序是按照区间左端点来排序的
     *
     * @since 2022-12-24
     */
    public static DoubleInterval[] sort(DoubleInterval[] intervals, boolean increasing) {
        if (increasing) { // 升序排列
            Arrays.sort(intervals, (first, second) -> Double.compare(first.start, second.start));
        } else {
            Arrays.sort(intervals, (first, second) -> Double.compare(second.start, first.start));
        }
        return intervals;
    }

    /**
     * @since 2022-11-21
     * @return 包含或相交时，返回 true。不相交时，返回 false
     */
    public static boolean intersectOrContain(DoubleInterval first, DoubleInterval second) {
        return first.start < second.start + second.length && second.start < first.start + first.length;
    }

    /**
     * 这是按照区间左端点判断大小的
     *
     * @since 2023-2-11
     */
    @Override
    public int compareTo(DoubleInterval other) {
        /**
         * 注意：不能使用此代码：return (int) (this.start - other.start);
         * 因为当 this.start 与 other.start 相差小于 1 时，此值将永远为 0
         */
        return Double.compare(this.start, other.start);
    }

    /**
     * @since 2023-2-11
     */
    @Override
    public String toString() {
        return this.toStringByEndpoint();
    }

    /**
     * 以【起点 + 长度】来显示区间
     *
     * @since 2023-2-11
     */
    public String toStringByLength() {
        return "DoubleInterval{" +
                "start=" + this.start +
                ", length=" + this.length +
                '}';
    }

    /**
     * 以【起点 + 终点】来显示区间
     *
     * @since 2023-2-11
     */
    public String toStringByEndpoint() {
        return "DoubleInterval{" +
                "start=" + this.start +
                ", end=" + (this.start + this.length) +
                '}';
    }
}
